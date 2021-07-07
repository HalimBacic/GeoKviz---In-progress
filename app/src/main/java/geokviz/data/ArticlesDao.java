package geokviz.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import geokviz.Articles;

@Dao
public interface ArticlesDao {

    @Insert
    void insertdb(List<Articles> list);

    @Query("Select * from Articles")
    List<Articles> getAll();

    @Query("Delete from Articles")
    void deleteAll();
}
