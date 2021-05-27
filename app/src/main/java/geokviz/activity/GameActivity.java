package geokviz.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import geokviz.Country;
import geokviz.data.CountryDao;
import geokviz.data.Countrydb;

public class GameActivity extends AppCompatActivity {

    Toolbar toolbar;
    Countrydb data;
    String something;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        setSupportActionBar(findViewById(R.id.guizToolbar));
        setTitle("Score ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        Countrydb db = Countrydb.getInstance(getApplicationContext());
                        something = db.getCountryDao().getAll().get(0).getCapitalCity();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),something,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
        ).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quizbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        else if(id == R.id.newsBtn)
            Toast.makeText(this,"Implementirati",Toast.LENGTH_LONG);
        return super.onOptionsItemSelected(item);
    }

}