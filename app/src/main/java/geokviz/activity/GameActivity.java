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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import geokviz.Country;
import geokviz.FlagQuestions;
import geokviz.LandmarksQuestions;
import geokviz.User;
import geokviz.fragments.InfoFragment;
import geokviz.fragments.NeighboursFragment;
import geokviz.NeighboursQuestions;
import geokviz.Question;
import geokviz.data.Countrydb;
import geokviz.fragments.FlagFragment;
import geokviz.fragments.LandmarkFragment;
import geokviz.fragments.QuestionFragment;

public class GameActivity extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<FlagQuestions> flagQuestions = new ArrayList<>();
    ArrayList<LandmarksQuestions> landQuestions = new ArrayList<>();
    ArrayList<NeighboursQuestions> neighboursQuestions = new ArrayList<>();
    QuestionFragment questionFragment;
    FlagFragment flagFragment;
    LandmarkFragment landFragment;
    NeighboursFragment neighboursFragment;
    Country c;
    Countrydb db;
    List<Country> list;
    ArrayList<Country> shuffler = new ArrayList<>();
    String LANG_CURRENT="sr";
    User user;

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
                        InitializeData();
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
    }

    private void InitializeData()
    {
        user = new User(getIntent().getExtras().getString("user"));

        makeQuestion(list);
        makeFlagQuestions(list);
        makeLandmarkQuestions(list);
        makeNeighboursQuestions(list);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("questions",questions);
        bundle.putParcelableArrayList("flags",flagQuestions);
        bundle.putParcelableArrayList("landmarks",landQuestions);
        bundle.putParcelableArrayList("neighbours",neighboursQuestions);
        bundle.putParcelable("userProfile",user);

        questionFragment = new QuestionFragment();
        openFragment(questionFragment,bundle);
    }

    private void makeNeighboursQuestions(List<Country> list)
    {
        Random rand = new Random();
        ArrayList<Integer> numbers = new ArrayList();
        for(int i=0;i<10;i++)
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
            shuffler.add(gc);
            numbers.add(num);

            //Generisanje pogrešnih odgovora
            List<String> allNeighbours = db.getCountryDao().getAllNeighbours();
            List<String> allNeighboursCorrected = new ArrayList<>();
            for (String aln: allNeighbours) {
                String[] alns = aln.split(" ");
                for (String alnss: alns) {
                    allNeighboursCorrected.add(alnss);
                }
            }

            Collections.shuffle(allNeighboursCorrected);

            ArrayList<String> neighbours = gc.getNeighbours();
            ArrayList<String> wrongs = new ArrayList<>();
            Integer c = 0;
            while(wrongs.size()<3)
            {
                String n = allNeighboursCorrected.get(c++);
                boolean status2 = true;
                for (String str:neighbours) {
                    if(str.equals(n)) status2 = false;
                }

                if(status2) {
                    wrongs.add(n);
                }
            }

            NeighboursQuestions nq = new NeighboursQuestions(gc.getCountryName(),neighbours,wrongs);
            neighboursQuestions.add(nq);
        }
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
            shuffler.add(gc);
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
            LandmarksQuestions lc = new LandmarksQuestions(gc.getLandmarks().get(brojLendmarka-1),gc.getCountryName(),wrongs,gc.getContinent());
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
            shuffler.add(qc);
            name = qc.getCountryName(); flag = qc.getCountryName().toLowerCase();
            String hint = qc.getContinent();
            ArrayList<String> chars = new ArrayList<>();

            int secureLetters = name.length();
            for(int k=0;k<secureLetters;k++)
                chars.add(String.valueOf(name.toCharArray()[k]).toLowerCase());

            //Postavljanje ostalih slova
            for(int j=0;j<20-secureLetters;j++)
            {
                chars.add(String.valueOf("abcdefghijklmnopqrstuvwxyz".toCharArray()[rand.nextInt("abcdefghijklmnopqrstuvwxyz".toCharArray().length)]));
            }

            FlagQuestions q = new FlagQuestions(name,flag,chars,hint);
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
                num = rand.nextInt(listSize);
                if(!numbers.isEmpty())
                    for (Integer n : numbers) {
                        if(n==num) status = false;
                }
            }
            numbers.add(num);
            Country qc = list.get(num);
            shuffler.add(qc);
            String hint = qc.getContinent();
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

            Question q = new Question(name,correct,incorrects,hint);
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
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if(!(fragment instanceof InfoFragment))
                this.finish();
            else
                getSupportFragmentManager().popBackStackImmediate();
        }
        else if(id == R.id.infoBtn) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if(fragment instanceof QuestionFragment) {
                QuestionFragment qf = (QuestionFragment) fragment;
                if(qf.getNextBtn().getVisibility()== View.VISIBLE) {
                    qf.checkIsValid(qf.getChecked());
                    Country c = shuffler.get(qf.getQnum());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("country", c);
                    InfoFragment infofrag = new InfoFragment();
                    openFragment(infofrag, bundle);
                }
                else
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.youmustanswer),Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.temporary),Toast.LENGTH_LONG).show();
            }
        }

        else if(id == R.id.hint)
        {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if(user.getUsedHints()>0)
                processFragmentHint(fragment);
            else {
                String msg = getResources().getString(R.string.noHintAnyMore);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void processFragmentHint(Fragment fragment) {
        if(fragment instanceof QuestionFragment)
        {
            QuestionFragment qf = (QuestionFragment) fragment;
            Toast.makeText(getApplicationContext(),qf.getCurrentQuestion().getHint(),Toast.LENGTH_LONG).show();
            Integer nh = qf.getUser().getUsedHints();
            qf.getUser().setUsedHints(nh-1);
        }
        else if(fragment instanceof FlagFragment)
        {
            FlagFragment qf = (FlagFragment) fragment;
            Toast.makeText(getApplicationContext(),qf.getCurrentQuestion().getHint(),Toast.LENGTH_LONG).show();
            if(qf.getFlagText().getText().equals(""))
            {
                qf.getFlagText().setText(qf.getCurrentQuestion().getAnswer().substring(0,1));
            }
            Integer nh = qf.getUser().getUsedHints();
            qf.getUser().setUsedHints(nh-1);
        }
        else if(fragment instanceof LandmarkFragment)
        {
            LandmarkFragment qf = (LandmarkFragment) fragment;
            Toast.makeText(getApplicationContext(),qf.getCurrentQuestion().getHint(),Toast.LENGTH_LONG).show();
            Integer nh = qf.getUser().getUsedHints();
            qf.getUser().setUsedHints(nh-1);
        }
        else if(fragment instanceof NeighboursFragment)
        {
            NeighboursFragment qf = (NeighboursFragment) fragment;
            NeighboursQuestions hq = qf.getCurrentQuestion();
            int temp = 0; Random rand = new Random();
            while(temp<2)
            {
                int temp2 = rand.nextInt(4);
                switch (temp2)
                {
                    case 0:
                        if(!hq.checkNeighbours(qf.getAnsA().getText().toString()) && !"".equals(qf.getAnsA().getText().toString()))
                        {
                            temp++; qf.getAnsA().setText("");
                        }
                        break;
                    case 1:
                        if(!hq.checkNeighbours(qf.getAnsB().getText().toString()) && !"".equals(qf.getAnsB().getText().toString()))
                        {
                            temp++; qf.getAnsB().setText("");
                        }
                        break;
                    case 2:
                        if(!hq.checkNeighbours(qf.getAnsC().getText().toString()) && !"".equals(qf.getAnsC().getText().toString()))
                        {
                            temp++; qf.getAnsC().setText("");
                        }
                        break;
                    case 3:
                        if(!hq.checkNeighbours(qf.getAnsD().getText().toString()) && !"".equals(qf.getAnsD().getText().toString()))
                        {
                            temp++; qf.getAnsD().setText("");
                        }
                        break;
                }
            }
            Integer nh = qf.getUser().getUsedHints();
            qf.getUser().setUsedHints(nh-1);
        }
    }

    public void openFragment(Fragment fragment,Bundle bundle)
    {
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer,fragment,"");
        ft.addToBackStack("");
        ft.commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        LANG_CURRENT = preferences.getString("Language", "sr-rBA");

        super.attachBaseContext(MyContextWrapper.wrap(newBase, LANG_CURRENT));
    }
}