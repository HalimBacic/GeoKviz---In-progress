package geokviz.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import geokviz.Country;

@Dao
public interface CountryDao {

    @Insert
    void insertdb(Country... contacts);   //TODO Check

    @Query("Select * from Country")
    List<Country> getAll();

    @Query("Select * from Country where level= :l")
    List<Country> getOnLevel(int l);

    @Query("Select neighbours from Country where countryName != :name")
    List<String> getNeighbours(String name);

    @Query("Select landmarks from country where countryName != :name")
    List<String> getLandmarks(String name);

    @Query("Select neighbours from Country where continent != :cont")
    List<String> getAllFrom(String cont);

    @Query("Select neighbours from Country")
    List<String> getAllNeighbours();

}
