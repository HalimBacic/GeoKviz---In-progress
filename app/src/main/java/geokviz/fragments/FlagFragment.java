package geokviz.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;

import geokviz.FlagQuestions;
import geokviz.Question;
import geokviz.User;
import geokviz.UserAnswerQuestion;
import geokviz.activity.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FlagFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlagFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<FlagQuestions> flags = new ArrayList<>();
    ArrayList<TextView> chars = new ArrayList<>();
    User user;
    TextView flagText;
    ImageView imageView;
    ImageButton nextBtn;
    Toolbar toolbar;
    int qpnum;
    int qnum=0;

    public FlagFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FlagFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FlagFragment newInstance(String param1, String param2) {
        FlagFragment fragment = new FlagFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TextView getFlagText() {
        return flagText;
    }

    public void setFlagText(TextView flagText) {
        this.flagText = flagText;
    }

    @Override
    public void onStart() {
        super.onStart();
        imageView = (ImageView) getView().findViewById(R.id.imageFrame);
        nextBtn = (ImageButton) getView().findViewById(R.id.nextBtn);
        nextBtn.setClickable(false);

        toolbar = (Toolbar) getActivity().findViewById(R.id.guizToolbar);
        MenuItem info = toolbar.getMenu().findItem(R.id.infoBtn);
        info.setVisible(false);

        chars.add((TextView) getView().findViewById(R.id.flagAns1));
        chars.add((TextView) getView().findViewById(R.id.flagAns2));
        chars.add((TextView) getView().findViewById(R.id.flagAns3));
        chars.add((TextView) getView().findViewById(R.id.flagAns4));
        chars.add((TextView) getView().findViewById(R.id.flagAns5));
        chars.add((TextView) getView().findViewById(R.id.flagAns6));
        chars.add((TextView) getView().findViewById(R.id.flagAns7));
        chars.add((TextView) getView().findViewById(R.id.flagAns8));
        chars.add((TextView) getView().findViewById(R.id.flagAns9));
        chars.add((TextView) getView().findViewById(R.id.flagAns10));
        chars.add((TextView) getView().findViewById(R.id.flagAns11));
        chars.add((TextView) getView().findViewById(R.id.flagAns12));
        chars.add((TextView) getView().findViewById(R.id.flagAns13));
        chars.add((TextView) getView().findViewById(R.id.flagAns14));
        chars.add((TextView) getView().findViewById(R.id.flagAns15));
        chars.add((TextView) getView().findViewById(R.id.flagAns16));
        chars.add((TextView) getView().findViewById(R.id.flagAns17));
        chars.add((TextView) getView().findViewById(R.id.flagAns18));
        chars.add((TextView) getView().findViewById(R.id.flagAns19));
        chars.add((TextView) getView().findViewById(R.id.flagAns20));
        flagText = (TextView) getView().findViewById(R.id.flagText);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    checkIsValid();
            }
        });

        for (TextView tview: chars ) {
            tview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String flag = flagText.getText().toString();
                    flag+=tview.getText();
                    flagText.setText(flag);
                }
            });
        }

        Initialize();
    }



    private void checkIsValid() {
        FlagQuestions currentQuestion = flags.get(qnum-1);
        TextView text = (TextView) getActivity().findViewById(R.id.flagText);
        String input = text.getText().toString();
        if(input.equals(currentQuestion.getAnswer())) {
            text.setBackgroundResource(R.color.correct);
            UserAnswerQuestion uaq = new UserAnswerQuestion(getResources().getString(R.string.flag)+currentQuestion.getAnswer(),text.getText().toString(),true);
            Integer points = user.getPoints()+10;
            user.setPoints(points);
            user.addQuestion(uaq);

            androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.guizToolbar);
            String scoreText = getResources().getString(R.string.score);
            scoreText+=" "+user.getPoints().toString();
            toolbar.setTitle(scoreText);
        }
        else {
            text.setBackgroundResource(R.color.incorrect);
            UserAnswerQuestion uaq = new UserAnswerQuestion(getResources().getString(R.string.flag)+currentQuestion.getAnswer(),text.getText().toString(),false);
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
                            LandmarkFragment fragment = new LandmarkFragment();
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

    private void Initialize() {
        ResetColors();
        Random rand = new Random();
        Collections.shuffle(flags.get(qnum).getChars());
        for(int i=0;i<20;i++)
        {
            chars.get(i).setText(flags.get(qnum).getChars().get(i));
        }

        String flagname = "ic_"+flags.get(qnum).getFlag().toLowerCase();
        int id = getResources().getIdentifier(flagname,"mipmap",getContext().getPackageName());
        imageView.setImageResource(id);
        qnum++;
    }

    private void ResetColors() {
        flagText.setText("");
        flagText.setBackgroundResource(R.color.primaryDarkColorDay);
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
        View view = inflater.inflate(R.layout.fragment_flag, container, false);

        if (savedInstanceState != null) {
            savedInstanceState = getArguments();
            flags = savedInstanceState.getParcelableArrayList("flags");
        }
        else
        {
            Bundle bundle = getArguments();
            user = bundle.getParcelable("userProfile");
            flags =  bundle.getParcelableArrayList("flags");
            SharedPreferences preferences = getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
            qpnum = preferences.getInt("qnumber",10);
        }
        return view;
    }

    public FlagQuestions getCurrentQuestion()
    {
        return flags.get(qnum-1);
    }

    public User getUser() {
        return user;
    }
}