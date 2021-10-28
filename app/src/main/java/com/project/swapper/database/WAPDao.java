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

    @Query("UPDATE wap SET password = :password WHERE bssid = :bssid")
    void update(String bssid, String password);

    @Query("DELETE FROM wap WHERE bssid = :bssid")
    void delete(String bssid);

    @Query("SELECT * FROM wap")
    List<WAPItem> listAll();

    @Query("SELECT * FROM wap WHERE bssid = :bssid")
    List<WAPItem> contains(String bssid);
}
