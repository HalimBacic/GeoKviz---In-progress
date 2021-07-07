package geokviz.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import geokviz.NeighboursQuestions;
import geokviz.Question;
import geokviz.User;
import geokviz.UserAnswerQuestion;
import geokviz.activity.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NeighboursFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NeighboursFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<NeighboursQuestions> questions;
    TextView question;
    TextView ansA;
    TextView ansB;
    TextView ansC;
    TextView ansD;
    Toolbar toolbar;
    User user;
    int qnum=0;
    int qpnum;

    public NeighboursFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NeighboursFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NeighboursFragment newInstance(String param1, String param2) {
        NeighboursFragment fragment = new NeighboursFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_neighbours, container, false);
        if (savedInstanceState != null) {
            savedInstanceState = getArguments();
            questions = savedInstanceState.getParcelableArrayList("neighbours");
        }
        else
        {
            Bundle bundle = getArguments();
            questions =  bundle.getParcelableArrayList("neighbours");
            user = bundle.getParcelable("userProfile");
            SharedPreferences preferences = getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
            qpnum = preferences.getInt("qnumber",10);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        question = (TextView)getView().findViewById(R.id.question);
        ansA = (TextView)getView().findViewById(R.id.ansA); ansB = (TextView)getView().findViewById(R.id.ansB);
        ansC = (TextView)getView().findViewById(R.id.ansC); ansD = (TextView)getView().findViewById(R.id.ansD);

        toolbar = (Toolbar) getActivity().findViewById(R.id.guizToolbar);
        MenuItem info = toolbar.getMenu().findItem(R.id.infoBtn);
        info.setVisible(false);

        ansA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ansB.setClickable(false); ansD.setClickable(false); ansC.setClickable(false);
                checkIsValid(ansA);
            }
        });

        ansB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ansA.setClickable(false); ansD.setClickable(false); ansC.setClickable(false);
                checkIsValid(ansB);
            }
        });

        ansC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ansA.setClickable(false); ansB.setClickable(false); ansD.setClickable(false);
                checkIsValid(ansC);
            }
        });

        ansD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ansA.setClickable(false); ansB.setClickable(false); ansC.setClickable(false);
                checkIsValid(ansD);
            }
        });

        Initialize();
    }

    private void checkIsValid(TextView textField) {
        NeighboursQuestions currentQuestion = questions.get(qnum-1);
        if(checkAnswer(textField.getText().toString(),currentQuestion.getNeighbours())) {
            textField.setBackgroundResource(R.color.correct);
            UserAnswerQuestion uaq = new UserAnswerQuestion(getResources().getString(R.string.neighbours)+currentQuestion.getCountry(),textField.getText().toString(),true);
            Integer points = user.getPoints()+10;
            user.setPoints(points);
            user.addQuestion(uaq);

            androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.guizToolbar);
            String scoreText = getResources().getString(R.string.score);
            scoreText+=" "+user.getPoints().toString();
            toolbar.setTitle(scoreText);
        }
        else {
            textField.setBackgroundResource(R.color.incorrect);
            UserAnswerQuestion uaq = new UserAnswerQuestion(getResources().getString(R.string.neighbours)+currentQuestion.getCountry(),textField.getText().toString(),false);
            user.addQuestion(uaq);
        }
        delayAnswer();
    }

    private void delayAnswer() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(qnum<qpnum)
                            Initialize();
                        else
                        {
                            Bundle bundle = getArguments();
                            SuccesFragment fragment = new SuccesFragment();
                            fragment.setArguments(bundle);
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.fragmentContainer,fragment,"");
                            ft.commit();
                        }
                    }
                });
            }
        });
    }

    private boolean checkAnswer(String checker, ArrayList<String> neighbours) {
        for (String str: neighbours) {
            if(checker.equals(str))
                return true;
        }
        return false;
    }

    private void Initialize() {
        ResetColors();
        NeighboursQuestions qq = questions.get(qnum++);
        Integer neighboursSize = qq.getNeighbours().size();

        int landNameId = getResources().getIdentifier(qq.getCountry().toLowerCase().replace(" ","_"),"string",getContext().getPackageName());
        String landName = getResources().getString(landNameId);
        String q = getResources().getString(R.string.neighbours);
        String qt = q +"  "+ landName;
        question.setText(qt);

        Collections.shuffle(qq.getWrongs());
        Random random = new Random();
        int cnum = random.nextInt(3);
        switch(cnum)
        {
            case 0:
                ansA.setText(qq.getNeighbours().get(neighboursSize-1));
                ansB.setText(qq.getWrongs().get(0)); ansC.setText(qq.getWrongs().get(1)); ansD.setText(qq.getWrongs().get(2));
                break;
            case 1:
                ansB.setText(qq.getNeighbours().get(neighboursSize-1));
                ansA.setText(qq.getWrongs().get(0)); ansC.setText(qq.getWrongs().get(1)); ansD.setText(qq.getWrongs().get(2));
                break;
            case 2:
                ansC.setText(qq.getNeighbours().get(neighboursSize-1));
                ansB.setText(qq.getWrongs().get(0)); ansA.setText(qq.getWrongs().get(1)); ansD.setText(qq.getWrongs().get(2));
                break;
            case 3:
                ansD.setText(qq.getNeighbours().get(neighboursSize-1));
                ansB.setText(qq.getWrongs().get(0)); ansC.setText(qq.getWrongs().get(1)); ansA.setText(qq.getWrongs().get(2));
                break;
        }
    }

    private void ResetColors() {
        if(getView()!=null)
        {
            ansA.setClickable(true);
            ansB.setClickable(true);
            ansC.setClickable(true);
            ansD.setClickable(true);
            ansA.setBackgroundResource(R.color.primaryDarkColorDay);
            ansB.setBackgroundResource(R.color.primaryDarkColorDay);
            ansC.setBackgroundResource(R.color.primaryDarkColorDay);
            ansD.setBackgroundResource(R.color.primaryDarkColorDay);
        }
    }

    public User getUser() {
        return user;
    }

    public TextView getAnsA() {
        return ansA;
    }

    public TextView getAnsB() {
        return ansB;
    }

    public TextView getAnsC() {
        return ansC;
    }

    public TextView getAnsD() {
        return ansD;
    }

    public NeighboursQuestions getCurrentQuestion()
    {
        return questions.get(qnum-1);
    }
}