package geokviz;

import java.util.ArrayList;

public class NeighboursQuestions {

    private String country;
    private ArrayList<String> neighbours;
    private ArrayList<String> wrongs;

    public NeighboursQuestions(String country, ArrayList<String> neighbours, ArrayList<String> wrongs) {
        this.country = country;
        this.neighbours = neighbours;
        this.wrongs = wrongs;
    }

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
}
