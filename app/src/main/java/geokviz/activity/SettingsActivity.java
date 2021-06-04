package geokviz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Properties;

public class SettingsActivity extends AppCompatActivity {

    ImageButton backBtn;
    Button settingBtn;
    Button engBtn;
    Button srBtn;
    String LANG_CURRENT = "sr-rBA";
    public Boolean languageChanged=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        backBtn = (ImageButton) findViewById(R.id.backBtn);

        if(getIntent().hasExtra("lc"))
        languageChanged = getIntent().getExtras().getBoolean("lc");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(languageChanged)
                {
                    finishAffinity();
                    startActivity(new Intent(SettingsActivity.this,MainActivity.class));
                }
                else
                    finish();
            }
        });

        settingBtn = (Button) findViewById(R.id.qnumBtn);


        SharedPreferences sp = getSharedPreferences("preferences",MODE_PRIVATE);
        Integer value = sp.getInt("qnumber",10);
        String valueText = value.toString()+" "+ getResources().getString(R.string.questions);

        settingBtn.setText(valueText);

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current = settingBtn.getText().toString();
                if(current.equals(getResources().getText(R.string.numq10)))
                {
                    settingBtn.setText(getResources().getText(R.string.numq5));
                    change(5);
                }
                else
                {
                    settingBtn.setText(getResources().getText(R.string.numq10));
                    change(10);
                }
            }

            private void change(Integer s) {
                SharedPreferences sharedPreferences = getSharedPreferences("preferences",MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putInt("qnumber",s);
                edit.commit();
            }
        });

        engBtn = (Button) findViewById(R.id.engBtn);
        srBtn = (Button) findViewById(R.id.srBtn);

        engBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLang(SettingsActivity.this,"en");
                finish();
                Intent settInt = new Intent(SettingsActivity.this,SettingsActivity.class);
                settInt.putExtra("lc",true);
                startActivity(settInt);
            }
        });

        srBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLang(SettingsActivity.this,"sr");
                finish();
                Intent settInt = new Intent(SettingsActivity.this,SettingsActivity.class);
                settInt.putExtra("lc",true);
                startActivity(settInt);
            }
        });
    }

    public void changeLang(Context context, String lang) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Language", lang);
        editor.apply();
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        LANG_CURRENT = preferences.getString("Language", "sr-rBA");

        super.attachBaseContext(MyContextWrapper.wrap(newBase, LANG_CURRENT));
    }
}