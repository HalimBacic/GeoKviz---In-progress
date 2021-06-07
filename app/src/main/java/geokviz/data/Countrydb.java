package geokviz.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import geokviz.Country;

@Database(entities = {Country.class}, version = 1)
@TypeConverters({CountryConverter.class})
public abstract class Countrydb extends RoomDatabase {
    private static Countrydb countrydb;


    public abstract CountryDao getCountryDao();

    public static  Countrydb getInstance(Context context) {
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
