package geokviz;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class Country {

    @PrimaryKey
    @NonNull
    private String countryName;
    private String capitalCity;
    private ArrayList<String> neighbours = new ArrayList<>();
    private ArrayList<String> landmarks = new ArrayList<>();
    private String continent;
    private int level;

    public Country() {
    }

    public Country(String countryName,String capitalCity, String neighbours,String landmarks,  String info, String continent,int level) {
        this.countryName = countryName;
        this.capitalCity = capitalCity;

        this.neighbours.addAll(Arrays.asList(neighbours.split(" ")));

        this.landmarks.addAll(Arrays.asList(neighbours.split(" ")));
        this.continent = continent;
        this.level = level;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getLevel() {
        return level;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public ArrayList<String> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(ArrayList<String> neighbours) {
        this.neighbours = neighbours;
    }

    public ArrayList<String> getLandmarks() {
        return landmarks;
    }

    public void setLandmarks(ArrayList<String> landmarks) {
        this.landmarks = landmarks;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}
