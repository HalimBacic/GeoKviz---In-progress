package geokviz.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import geokviz.User;

@Database(entities = {User.class}, version = 1)
@TypeConverters({QuestionConverter.class})
public abstract class Userdb extends RoomDatabase {

    private static Userdb userdb;


    public abstract UserDao getCountryDao();

    public static  Userdb getInstance(Context context) {
        if (null == userdb) {
            userdb = buildDatabaseInstance(context);
        }
        return userdb;
    }

    private static Userdb buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                Userdb.class,"user.db").createFromAsset("base.db").build();
    }

    public void cleanUp(){
        userdb = null;
    }
}
