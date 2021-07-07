package geokviz.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import geokviz.Articles;

@Database(entities = {Articles.class}, version = 1)
@TypeConverters({SourceConverter.class})
public abstract class Articlesdb extends RoomDatabase {

    private static Articlesdb articlesdb;


    public abstract ArticlesDao getArticlesDao();

    public static  Articlesdb getInstance(Context context) {
        if (null == articlesdb) {
            articlesdb = buildDatabaseInstance(context);
        }
        return articlesdb;
    }

    private static Articlesdb buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                Articlesdb.class,"articles.db").createFromAsset("base.db").build();
    }

    public void cleanUp(){
        articlesdb = null;
    }

}
