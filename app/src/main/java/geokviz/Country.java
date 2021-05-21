package geokviz;

import java.util.ArrayList;

public class Country {

    private String capitalCity;
    private String flag;
    private ArrayList<String> neighbours;
    private ArrayList<String> landmarks;
    private String capitalPin;
    private String info;
    private String continent;

    public Country() {
    }

    public Country(String capitalCity, String flag, ArrayList<String> neighbours, ArrayList<String> landmarks, String capitalPin, String info, String continent) {
        this.capitalCity = capitalCity;
        this.flag = flag;
        this.neighbours = neighbours;
        this.landmarks = landmarks;
        this.capitalPin = capitalPin;
        this.info = info;
        this.continent = continent;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getCapitalPin() {
        return capitalPin;
    }

    public void setCapitalPin(String capitalPin) {
        this.capitalPin = capitalPin;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}
