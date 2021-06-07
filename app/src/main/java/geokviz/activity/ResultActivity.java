package geokviz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import geokviz.User;
import geokviz.data.Countrydb;
import geokviz.data.Userdb;

public class ResultActivity extends AppCompatActivity {

    Userdb userdb;
    List<User> users = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        new Thread(
                () -> {

                    userdb = Userdb.getInstance(getApplicationContext());
                    runOnUiThread(() -> {

                    });
                }
        ).start();

        try {
            users = userdb.getCountryDao().getAll();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),getResources().getText(R.string.empty),Toast.LENGTH_LONG).show();
        }

        recyclerView = findViewById(R.id.resultRecycler);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new Adapter((ArrayList<User>) users);
        recyclerView.setAdapter(adapter);
    }
}