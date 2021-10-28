package com.project.swapper.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Overall WAP database object.
 * Ref. COMP5216 Wk3 Lab sheet
 */
@Database(entities = { WAPItem.class }, version = 1, exportSchema = false)
public abstract class WAPDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "wap_db";
    private static WAPDatabase DBINSTANCE;

    public abstract WAPDao wapDao();

    public static WAPDatabase getDatabase(Context context) {
        if (DBINSTANCE == null) {
            synchronized (WAPDatabase.class) {
                DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        WAPDatabase.class, DATABASE_NAME).build();
            }
        }
        return DBINSTANCE;
    }

    public static void destroyInstance() {
        DBINSTANCE = null;
    }
}
