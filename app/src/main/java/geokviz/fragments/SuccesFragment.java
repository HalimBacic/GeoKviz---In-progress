package geokviz.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import geokviz.MyView;
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
    View view;
    Bitmap bitmap;
    private ImageView img;
    private TextView upText;
    private TextView downText;
    private ImageButton instShare;
    private ImageButton fbShare;
    User user;
    Userdb userdb;
    Integer maxPoints;
    Toolbar toolbar;

    public SuccesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        toolbar = (Toolbar) getActivity().findViewById(R.id.guizToolbar);
        MenuItem info = toolbar.getMenu().findItem(R.id.infoBtn);
        info.setVisible(false);
        MenuItem hint = toolbar.getMenu().findItem(R.id.hint);
        hint.setVisible(false);
        MenuItem share = toolbar.getMenu().findItem(R.id.share);
        share.setVisible(true);

        share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                bitmap = getBitmapFromView(view);
                String filename = saveToInternalStorage(bitmap);  //Save image on internal system

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                File mFileImagePath = new File(filename,"geo.jpg");  // Just example you use file URL

                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                if (intent != null) {
                    Intent mIntentShare = new Intent(Intent.ACTION_SEND);
                    String mStrExtension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(mFileImagePath).toString());
                    String mStrMimeType = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(mStrExtension);
                    if (mStrExtension.equalsIgnoreCase("") || mStrMimeType == null) {
                        // if there is no extension or there is no definite mimetype, still try to open the file
                        mIntentShare.setType("text*//*");
                    } else {
                        mIntentShare.setType(mStrMimeType);
                    }
                    mIntentShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mFileImagePath));
                    mIntentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    mIntentShare.setPackage("com.instagram.android");
                    startActivity(mIntentShare);
                } else {
                    Toast.makeText(getContext(), "Instagram have not been installed.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        userNameSucces = (TextView) getActivity().findViewById(R.id.userNameSucces);
        img= (ImageView) getActivity().findViewById(R.id.image);
        String userText = user.getUsername()+"  "+user.getPoints();
        view = (RelativeLayout) getActivity().findViewById(R.id.mylayout);
        upText = (TextView) getActivity().findViewById(R.id.upText);
        upText.setText(userText);

        downText = (TextView) getActivity().findViewById(R.id.downText);

        //Računanje uspješnosti
        SharedPreferences sp = getActivity().getSharedPreferences("preferences",getActivity().MODE_PRIVATE);
        Integer questions = sp.getInt("qnumber",10);
        maxPoints = 4 * questions * 10;
        Double percent = 100.0*user.getPoints()/maxPoints;

        setDownText(percent);

        userNameSucces.setText(userText);


        userdb = Userdb.getInstance(getActivity().getApplicationContext());
        UserDao userDao = userdb.getUserDao();


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

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"geo.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private void setDownText(Double percent) {
        if (percent >= 0 && percent < 20) {
            downText.setText(getResources().getString(R.string.level1));
            img.setImageResource(R.drawable.globe1);
        }
        else if (percent >= 20 && percent < 40) {
            downText.setText(getResources().getString(R.string.level2));
            img.setImageResource(R.drawable.globe2);
        }
        else if(percent >=40 && percent <60) {
            downText.setText(getResources().getString(R.string.level3));
            img.setImageResource(R.drawable.globe3);
        }
        else if(percent >=60 && percent <80) {
            downText.setText(getResources().getString(R.string.level4));
            img.setImageResource(R.drawable.globe4);
        }
        else if(percent >=80 && percent <100) {
            downText.setText(getResources().getString(R.string.level5));
            img.setImageResource(R.drawable.globe5);
        }

        view.setDrawingCacheEnabled(true);
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

    public Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(700, 900,Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
        {
            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.primaryLightColorDay,null));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPaint(paint);

            paint.setColor(getResources().getColor(R.color.secondaryColorDay,null));
            paint.setTextSize(50);
            canvas.drawText(upText.getText()+"\n\n"+downText.getText(), 100, 500, paint);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
}