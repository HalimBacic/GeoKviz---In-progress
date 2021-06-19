package geokviz.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import geokviz.User;
import geokviz.activity.R;
import geokviz.data.UserDao;
import geokviz.data.Userdb;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuccesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuccesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView userNameSucces;
    User user;
    Userdb userdb;

    public SuccesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        userNameSucces = (TextView) getActivity().findViewById(R.id.userNameSucces);
        String userText = user.getUsername()+"  "+user.getPoints();


        //TODO Load image

        userNameSucces.setText(userText);

        userdb = Userdb.getInstance(getActivity().getApplicationContext());
        UserDao userDao = userdb.getUserDao();

        //TODO Testirati...

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        userDao.insertdb(user);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }
        ).start();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment succesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuccesFragment newInstance(String param1, String param2) {
        SuccesFragment fragment = new SuccesFragment();
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
        View view =  inflater.inflate(R.layout.fragment_succes, container, false);
        if (savedInstanceState != null) {
            savedInstanceState = getArguments();
        }
        else
        {
            Bundle bundle = getArguments();
            user = bundle.getParcelable("userProfile");
        }

        return view;
    }
}