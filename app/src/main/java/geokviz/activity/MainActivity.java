package geokviz.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import geokviz.Country;
import geokviz.data.CountryDao;
import geokviz.data.Countrydb;

public class MainActivity extends AppCompatActivity {

    ImageButton settingsBtn;
    TextInputEditText username;
    Button resBtn;
    Button exitBtn;
    Button newGameBtn;
    String LANG_CURRENT = "sr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.unField);

        resBtn = (Button)findViewById(R.id.resBtn);
        resBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultsIntent = new Intent(MainActivity.this,ResultActivity.class);
                startActivity(resultsIntent);
            }
        });

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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(MainActivity.this,GameActivity.class);
                String user="";
                if(username.getText().equals(""))
                    user = LocalTime.now().toString()+" Unknown";
                else
                    user = username.getText().toString();
                gameIntent.putExtra("user",user);
                startActivity(gameIntent);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        LANG_CURRENT = preferences.getString("Language", "sr-rBA");

        super.attachBaseContext(MyContextWrapper.wrap(newBase, LANG_CURRENT));
    }
}