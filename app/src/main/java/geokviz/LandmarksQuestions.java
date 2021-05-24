package geokviz;

import java.util.ArrayList;

public class LandmarksQuestions {

    private String landmark;
    private String country;
    private ArrayList<String> wrongs;

    public LandmarksQuestions(String landmark, String country, ArrayList<String> wrongs) {
        this.landmark = landmark;
        this.country = country;
        this.wrongs = wrongs;
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
