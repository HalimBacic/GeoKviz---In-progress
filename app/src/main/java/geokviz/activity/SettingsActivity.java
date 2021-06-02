package geokviz.activity;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Properties;

public class SettingsActivity extends AppCompatActivity {

    ImageButton backBtn;
    Button settingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        backBtn = (ImageButton) findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        settingBtn = (Button) findViewById(R.id.qnumBtn);
        Properties properties = new Properties();;
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("app.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String currentNum = properties.getProperty("qnum");
        settingBtn.setText(currentNum+" Questions");
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current = settingBtn.getText().toString();
                if(current.equals("10 Questions"))
                {
                    settingBtn.setText("5 Questions");
                    change("5");
                }
                else
                {
                    settingBtn.setText("10 Questions");
                    change("10");
                }
            }

            private void change(String s) {
                try {
                    properties.setProperty("qnum",s);
                    properties.store(new FileWriter("app.properties"),"");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(s);
            }
        });
    }
}