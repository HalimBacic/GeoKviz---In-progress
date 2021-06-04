package geokviz.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import geokviz.Question;
import geokviz.activity.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Question> questions;
    TextView question;
    TextView ansA;
    TextView ansB;
    TextView ansC;
    TextView ansD;
    ImageButton next;
    int qnum=0;
    int qpnum;

    @Override
    public void onStart() {
        super.onStart();
        next = (ImageButton)getView().findViewById(R.id.nextQuestion);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qnum<qpnum)
                    Initialize();
            }
        });

        question = (TextView)getView().findViewById(R.id.question);
        ansA = (TextView)getView().findViewById(R.id.ansA); ansB = (TextView)getView().findViewById(R.id.ansB);
        ansC = (TextView)getView().findViewById(R.id.ansC); ansD = (TextView)getView().findViewById(R.id.ansD);

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
        Question currentQuestion = questions.get(qnum-1);
        if(textField.getText() == currentQuestion.getCorrect())
            textField.setBackgroundResource(R.color.correct);
        else
            textField.setBackgroundResource(R.color.incorrect);
    }

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
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
        View view =  inflater.inflate(R.layout.fragment_question, container, false);
        if (savedInstanceState != null) {
            savedInstanceState = getArguments();
            questions = savedInstanceState.getParcelableArrayList("questions");
        }
        else
        {
            Bundle bundle = getArguments();
            questions =  bundle.getParcelableArrayList("questions");
            SharedPreferences preferences = getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
            qnum = preferences.getInt("qnumber",10);
        }

        return view;
    }

    private void Initialize() {
        ResetColors();
        Question qq = questions.get(qnum++);
        question.setText(qq.question);
        Random random = new Random();
        int cnum = random.nextInt(3);
        switch(cnum)
        {
            case 0:
                ansA.setText(qq.correct);
                ansB.setText(qq.incorrect.get(0)); ansC.setText(qq.incorrect.get(1)); ansD.setText(qq.incorrect.get(2));
                break;
            case 1:
                ansB.setText(qq.correct);
                ansA.setText(qq.incorrect.get(0)); ansC.setText(qq.incorrect.get(1)); ansD.setText(qq.incorrect.get(2));
                break;
            case 2:
                ansC.setText(qq.correct);
                ansB.setText(qq.incorrect.get(0)); ansA.setText(qq.incorrect.get(1)); ansD.setText(qq.incorrect.get(2));
                break;
            case 3:
                ansD.setText(qq.correct);
                ansB.setText(qq.incorrect.get(0)); ansC.setText(qq.incorrect.get(1)); ansA.setText(qq.incorrect.get(2));
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
}