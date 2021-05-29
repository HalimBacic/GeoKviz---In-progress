package geokviz.fragments;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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
    TextView flagText;
    ImageView imageView;
    ImageButton nextBtn;
    ImageButton checkBtn;
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

    @Override
    public void onStart() {
        super.onStart();

        imageView = (ImageView) getView().findViewById(R.id.imageFrame);
        nextBtn = (ImageButton) getView().findViewById(R.id.nextBtn);
        nextBtn.setClickable(false);
        checkBtn = (ImageButton) getView().findViewById(R.id.checkBtn);
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

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = flagText.getText().toString();
                String name2 = flags.get(qnum).getFlag();
                if(name.equals(name2))
                    flagText.setBackgroundResource(R.color.correct);
                else
                    flagText.setBackgroundResource(R.color.incorrect);
                nextBtn.setClickable(true);
                qnum++;
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qnum<qpnum)
                    Initialize();
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
            flags =  bundle.getParcelableArrayList("flags");
            Properties properties = new Properties();
            AssetManager assetManager = getContext().getAssets();
            InputStream inputStream = null;
            try {
                inputStream = assetManager.open("app.properties");
                properties.load(inputStream);
                qpnum = Integer.parseInt(properties.getProperty("qnum"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}