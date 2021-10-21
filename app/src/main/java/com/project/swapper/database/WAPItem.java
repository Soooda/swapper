package com.project.swapper.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity Object for Table "wap".
 */
@Entity(tableName = "wap")
public class WAPItem {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "BSSID")
    private String bssid;

    @NonNull
    @ColumnInfo(name = "password") // Encrypted password
    private String password;

    public WAPItem(String bssid, String password) {
        this.bssid = bssid;
        this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public String getBssid() {
        return this.bssid;
    }

    public String getPassword() {
        return this.password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}
