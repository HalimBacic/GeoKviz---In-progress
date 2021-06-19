package geokviz;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NeighboursQuestions implements Parcelable {

    private String country;
    private ArrayList<String> neighbours;
    private ArrayList<String> wrongs;

    public NeighboursQuestions(String country, ArrayList<String> neighbours, ArrayList<String> wrongs) {
        this.country = country;
        this.neighbours = neighbours;
        this.wrongs = wrongs;
    }

    protected NeighboursQuestions(Parcel in) {
        country = in.readString();
        neighbours = in.createStringArrayList();
        wrongs = in.createStringArrayList();
    }

    public static final Creator<NeighboursQuestions> CREATOR = new Creator<NeighboursQuestions>() {
        @Override
        public NeighboursQuestions createFromParcel(Parcel in) {
            return new NeighboursQuestions(in);
        }

        @Override
        public NeighboursQuestions[] newArray(int size) {
            return new NeighboursQuestions[size];
        }
    };

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<String> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(ArrayList<String> neighbours) {
        this.neighbours = neighbours;
    }

    public ArrayList<String> getWrongs() {
        return wrongs;
    }

    public void setWrongs(ArrayList<String> wrongs) {
        this.wrongs = wrongs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(country);
        dest.writeStringList(neighbours);
        dest.writeStringList(wrongs);
    }

    public boolean checkNeighbours(String n)
    {
        for (String neig: neighbours ){
            if(neig.equals(n))
                return true;
        }
        return false;
    }
}
