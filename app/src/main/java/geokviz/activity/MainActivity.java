package geokviz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import geokviz.Country;
import geokviz.data.CountryDao;
import geokviz.data.Countrydb;

public class MainActivity extends AppCompatActivity {

    ImageButton settingsBtn;
    ImageButton engBtn;
    ImageButton rsBtn;
    Button resBtn;
    Button exitBtn;
    Button newGameBtn;
    Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resBtn = (Button)findViewById(R.id.resBtn);

        settingsBtn = (ImageButton) findViewById(R.id.settingBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent settingIntent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(settingIntent);
            }
        });

        exitBtn = (Button) findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newGameBtn = (Button) findViewById(R.id.newGameBtn);
        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(MainActivity.this,GameActivity.class);
                startActivity(gameIntent);
            }
        });

        engBtn = (ImageButton) findViewById(R.id.eng);
        engBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });

        rsBtn = (ImageButton) findViewById(R.id.rs);
        rsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}