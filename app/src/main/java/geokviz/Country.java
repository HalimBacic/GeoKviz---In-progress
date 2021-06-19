package geokviz;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class Country implements Parcelable {

    @PrimaryKey
    @NonNull
    private String countryName;
    private String capitalCity;
    private ArrayList<String> neighbours = new ArrayList<>();
    private ArrayList<String> landmarks = new ArrayList<>();
    private String continent;
    private int level;
    private Integer numberCapital;
    private Double latitude;
    private Double longitude;

    public Country() {
    }

    public Country(String countryName,String capitalCity, String neighbours,String landmarks,  String info, String continent,int level,int nic, Double x, Double y) {
        this.countryName = countryName;
        this.capitalCity = capitalCity;
        this.numberCapital = nic;
        this.latitude = x;
        this.longitude = y;
        this.neighbours.addAll(Arrays.asList(neighbours.split(" ")));

        this.landmarks.addAll(Arrays.asList(neighbours.split(" ")));
        this.continent = continent;
        this.level = level;
    }

    protected Country(Parcel in) {
        countryName = in.readString();
        capitalCity = in.readString();
        neighbours = in.createStringArrayList();
        landmarks = in.createStringArrayList();
        continent = in.readString();
        level = in.readInt();
        if (in.readByte() == 0) {
            numberCapital = null;
        } else {
            numberCapital = in.readInt();
        }
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

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

    public Integer getNumberCapital() {
        return numberCapital;
    }

    public void setNumberCapital(Integer numberCapital) {
        this.numberCapital = numberCapital;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countryName);
        dest.writeString(capitalCity);
        dest.writeStringList(neighbours);
        dest.writeStringList(landmarks);
        dest.writeString(continent);
        dest.writeInt(level);
        if (numberCapital == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numberCapital);
        }
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
    }
}
