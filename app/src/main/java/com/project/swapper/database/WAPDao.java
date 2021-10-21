package com.project.swapper.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Data Access Object (DAO) for WAP database.
 */
@Dao
public interface WAPDao {
    @Insert
    void insert(WAPItem wap);

    @Update
    void update(WAPItem wap);

    @Query("SELECT * FROM wap")
    List<WAPItem> listAll();

    @Query("SELECT * FROM wap WHERE bssid = :bssid")
    List<WAPItem> contains(String bssid);
}
