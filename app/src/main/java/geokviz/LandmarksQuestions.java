package geokviz;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class LandmarksQuestions implements Parcelable{

    private String landmark;
    private String country;
    private ArrayList<String> wrongs;

    public LandmarksQuestions(String landmark, String country, ArrayList<String> wrongs) {
        this.landmark = landmark;
        this.country = country;
        this.wrongs = wrongs;
    }


    public LandmarksQuestions(Parcel in) {
        landmark = in.readString();
        country = in.readString();
        wrongs = in.createStringArrayList();
    }

    public static final Creator<LandmarksQuestions> CREATOR = new Creator<LandmarksQuestions>() {
        @Override
        public LandmarksQuestions createFromParcel(Parcel in) {
            return new LandmarksQuestions(in);
        }

        @Override
        public LandmarksQuestions[] newArray(int size) {
            return new LandmarksQuestions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(landmark);
        dest.writeString(country);
        dest.writeStringList(wrongs);
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<String> getWrongs() {
        return wrongs;
    }

    public void setWrongs(ArrayList<String> wrongs) {
        this.wrongs = wrongs;
    }
}
