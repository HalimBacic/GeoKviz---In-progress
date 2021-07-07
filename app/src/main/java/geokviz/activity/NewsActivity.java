package geokviz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import geokviz.Adapter;
import geokviz.ApiClient;
import geokviz.Articles;
import geokviz.Headlines;
import geokviz.data.Articlesdb;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Articlesdb db;
    SwipeRefreshLayout swipeRefreshLayout;
    String etQuery="";
    String country = "us";
    Dialog dialog;
    public static final String API_KEY = "bce72b64d45b43ec9df7fc3e9c01ba67";
    Adapter adapter;
    List<Articles>  articles = new ArrayList<>();
    List<Articles>  articlesCache = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);

        etQuery = getIntent().getStringExtra("city");

        dialog = new Dialog(NewsActivity.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson(API_KEY);
            }
        });

        if(etQuery==null)
            etQuery="";
        retrieveJson(API_KEY);

    }

    public void retrieveJson(String apiKey){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        db = Articlesdb.getInstance(getApplicationContext());
                        articlesCache = db.getArticlesDao().getAll();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }
        ).start();

                swipeRefreshLayout.setRefreshing(true);
                String query;
                Call<Headlines> call;
                if (!etQuery.equals("")){
                    query = etQuery;
                    call= ApiClient.getInstance().getApi().getSpecificData(query,apiKey);
                }else{
                    call= ApiClient.getInstance().getApi().getSpecificData("Geography",apiKey); //getHeadlines(country,apiKey);
                }

                call.enqueue(new Callback<Headlines>() {
                    @Override
                    public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                        if (response.isSuccessful() && response.body().getArticles() != null){
                            swipeRefreshLayout.setRefreshing(false);
                            articles.clear();
                            articles = response.body().getArticles();

                            SharedPreferences sp = getSharedPreferences("preferences",MODE_PRIVATE);
                            Integer value = sp.getInt("cache",0);

                            if(value!=0)
                                makeCache(articles);
                            else
                            {
                                articlesCache.clear();
                                new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {

                                                db = Articlesdb.getInstance(getApplicationContext());
                                                db.getArticlesDao().deleteAll();
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                    }
                                                });
                                            }
                                        }
                                ).start();
                            }

                            adapter = new Adapter(NewsActivity.this,articles);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<Headlines> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                        SharedPreferences sp = getSharedPreferences("preferences",MODE_PRIVATE);
                        Integer value = sp.getInt("cache",0);
                        if(value==0)
                            Toast.makeText(NewsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(NewsActivity.this, getResources().getString(R.string.cachedMsg), Toast.LENGTH_LONG).show();
                            adapter = new Adapter(NewsActivity.this,articlesCache);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
    }

    private void makeCache(List<Articles> articles) {
        articlesCache.clear();
        for (Articles art:articles) {
            articlesCache.add(art);
        }
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        db = Articlesdb.getInstance(getApplicationContext());
                        db.getArticlesDao().deleteAll();
                        db.getArticlesDao().insertdb(articlesCache);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }
        ).start();
    }


    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
