package geokviz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import geokviz.User;
import geokviz.data.Userdb;
import geokviz.fragments.TableFragment;

public class ResultActivity extends AppCompatActivity {

    Userdb userdb;
    ArrayList<User> users = new ArrayList<>();
    Bundle bundle = new Bundle();
    TableFragment fragment = new TableFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        new Thread(
                () -> {

                    userdb = Userdb.getInstance(getApplicationContext());
                    try {
                        users = (ArrayList<User>) userdb.getUserDao().getAll();
                        bundle.putParcelableArrayList("results",users);

                        openFragment();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(() -> {

                    });
                }
        ).start();

    }

    private void openFragment() {
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentResults,fragment,"");
        ft.commit();
    }
}