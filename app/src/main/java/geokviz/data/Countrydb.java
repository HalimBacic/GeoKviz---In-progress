package geokviz.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import geokviz.Country;
import geokviz.activity.R;

@Database(entities = {Country.class}, version = 1)
@TypeConverters({CountryConverter.class})
public abstract class Countrydb extends RoomDatabase {

    /*
            Country[] allCountry = {
                new Country("Germany","Berlin","France Poland Austria","brandeburg","","Europe",1),
     */

    public abstract CountryDao getCountryDao();
    private static Countrydb countrydb;

    public static /*synchronized*/ Countrydb getInstance(Context context) {
        if (null == countrydb) {
            countrydb = buildDatabaseInstance(context);
        }
        return countrydb;
    }

    private static Countrydb buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                Countrydb.class,"country.db").createFromAsset("country.db").build();
    }

    public void cleanUp(){
        countrydb = null;
    }

}
