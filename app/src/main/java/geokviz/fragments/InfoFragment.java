package geokviz.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

import geokviz.Country;
import geokviz.activity.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Country country;
    private TextView cityName;
    private TextView citizens;
    private ImageView emblem;
    private MapView map;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();

        cityName = (TextView) getView().findViewById(R.id.cityName);
        citizens = (TextView) getView().findViewById(R.id.citizens);
        emblem = (ImageView) getView().findViewById(R.id.emblem);
        String emb = "ic_"+country.getCapitalCity().toLowerCase().replace(" ","")+"emblem";
        emblem.setImageResource(getResources().getIdentifier(emb,"mipmap",getContext().getPackageName()));

        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.i("DEBUG", "onMapReady");

                LatLng position = new LatLng(country.getLatitude(),country.getLongitude());
                Marker marker = googleMap.addMarker(new MarkerOptions().position(position).title(country.getCapitalCity()));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 0);
                googleMap.animateCamera(cameraUpdate);
            }
        });

        cityName.setText(country.getCapitalCity());
        DecimalFormat df = new DecimalFormat("###,###,###");
        String number = df.format(country.getNumberCapital());
        citizens.setText(number);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            country = getArguments().getParcelable("country");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_info, container, false);
        map = (MapView) view.findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
        return view;
    }
}