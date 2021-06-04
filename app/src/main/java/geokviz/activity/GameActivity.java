package geokviz.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import geokviz.Country;
import geokviz.FlagQuestions;
import geokviz.LandmarksQuestions;
import geokviz.Question;
import geokviz.data.Countrydb;
import geokviz.fragments.FlagFragment;
import geokviz.fragments.LandmarkFragment;
import geokviz.fragments.QuestionFragment;

public class GameActivity extends AppCompatActivity {

    Toolbar toolbar;
    Countrydb data;
    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<FlagQuestions> flagQuestions = new ArrayList<>();
    ArrayList<LandmarksQuestions> landQuestions = new ArrayList<>();
    QuestionFragment questionFragment;
    FlagFragment flagFragment;
    LandmarkFragment landFragment;
    Countrydb db;
    List<Country> list;
    String LANG_CURRENT="sr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        db = Countrydb.getInstance(getApplicationContext());
                        list = db.getCountryDao().getAll();
              //          makeQuestion(list);
              //          makeFlagQuestions(list);

                        makeLandmarkQuestions(list);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }
        ).start();

        setSupportActionBar(findViewById(R.id.guizToolbar));
        setTitle(R.string.score);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = new Bundle();
    //    bundle.putParcelableArrayList("questions",questions);
    //    bundle.putParcelableArrayList("flags",flagQuestions);
        bundle.putParcelableArrayList("landmarks",landQuestions);

    //    questionFragment = new QuestionFragment();
     //   flagFragment = new FlagFragment();
        landFragment = new LandmarkFragment();
        openFragment(landFragment,bundle);
    }

    private void makeLandmarkQuestions(List<Country> list)
    {
        Random rand = new Random();  // za svako slučajno generisanje broja
        ArrayList<Integer> numbers = new ArrayList<>(); //niz već odabranih pitanja
        for(int i=0;i<10;i++)  //10 puta se dodaje pitanje
        {
            Country gc;
            //Generisanje slobodnog broja
            boolean status = false;
            int num = 0;
            while(!status)
            {
                status = true;
                num = rand.nextInt(list.size()-1);
                if(!numbers.isEmpty())
                    for (Integer n : numbers) {
                        if(n==num) status = false;
                    }
                if(list.get(num).getLandmarks()==null)
                    status = false;
            }

            //Dobija se generisan broj iz predhodnog bloka za pitanje koje se dodaje
            gc = list.get(num);
            numbers.add(num);

            //Generisanje pogrešnih odgovora
            ArrayList<String> wrongs = new ArrayList<>();
            for(int j=0;j<4;j++)
            {
                ArrayList<Integer> nums = new ArrayList<>();
                int num2=0;
                boolean status2 = false;
                while(!status2)
                {
                    status2 = true;
                    num2 = rand.nextInt(list.size()-1);
                    if(!nums.isEmpty())
                        for (Integer n : nums) {
                            if(n==num2) status2 = false;
                        }
                }
                nums.add(num2);
                wrongs.add(list.get(num2).getCountryName());
            }
            int brojLendmarka = gc.getLandmarks().size();
            LandmarksQuestions lc = new LandmarksQuestions(gc.getLandmarks().get(brojLendmarka-1),gc.getCountryName(),wrongs);
            landQuestions.add(lc);
        }
    }

    private void makeFlagQuestions(List<Country> list) {
        Random rand = new Random();
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            String name; String flag; int num = 0; boolean status=false;

            while(!status)
            {
                status = true;
                num = rand.nextInt(list.size()-1);
                if(!numbers.isEmpty())
                    for (Integer n : numbers) {
                        if(n==num) status = false;
                    }
            }
            numbers.add(num);
            Country qc = list.get(num);
            name = qc.getCountryName(); flag = qc.getCountryName().toLowerCase();
            ArrayList<String> chars = new ArrayList<>();

            int secureLetters = name.length();
            for(int k=0;k<secureLetters;k++)
                chars.add(String.valueOf(name.toCharArray()[k]).toLowerCase());

            //Postavljanje ostalih slova
            for(int j=0;j<20-secureLetters;j++)
            {
                chars.add(String.valueOf("abcdefghijklmnopqrstuvwxyz".toCharArray()[rand.nextInt("abcdefghijklmnopqrstuvwxyz".toCharArray().length)]));
            }

            FlagQuestions q = new FlagQuestions(name,flag,chars);
            flagQuestions.add(q);
        }

    }

    private void makeQuestion(List<Country> list) {
        Random rand = new Random();
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            String name; String correct; int num = 0; boolean status=false;

            while(!status)
            {
                status = true;
                int listSize = list.size()-1;
                num = rand.nextInt(list.size()-1);
                if(!numbers.isEmpty())
                    for (Integer n : numbers) {
                        if(n==num) status = false;
                }
            }
            numbers.add(num);
            Country qc = list.get(num);
            name = qc.getCountryName(); correct = qc.getCapitalCity();
            ArrayList<String> incorrects = new ArrayList<>();

            //Postavljanje netačnih odgovora
            for(int j=0;j<3;j++)
            {
                status = false; int num2=0;
                while(!status)
                {
                    num2 = rand.nextInt(list.size()-1);
                    if(num2 != num) {
                        status = true;
                        String inc = list.get(num2).getCapitalCity();
                        for (String inct : incorrects)
                            if(inct==inc)  status=false;
                        if(status == true)
                        incorrects.add(list.get(num2).getCapitalCity());
                    }
                }
            }

            Question q = new Question(name,correct,incorrects);
            questions.add(q);
        }
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

    public void openFragment(Fragment fragment,Bundle bundle)
    {
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer,fragment,"");
        ft.commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        LANG_CURRENT = preferences.getString("Language", "sr-rBA");

        super.attachBaseContext(MyContextWrapper.wrap(newBase, LANG_CURRENT));
    }
}