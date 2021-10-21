package com.project.swapper.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * This class handles all database operations.
 */
public class DatabaseManager {
    private WAPDao dao;
    private List<WAPItem> waps;

    public DatabaseManager(Context context) {
        WAPDatabase db = WAPDatabase.getDatabase(context);
        dao = db.wapDao();
        read();
    }

    /**
     * Inserts the data into the database.
     * @param bssid The bssid of the network.
     * @param password The password of the network.
     */
    public void insert(String bssid, String password) {
        // Use asynchronous task to run query on the background to avoid locking UI
        try {
            // Run a task specified by a Runnable Object asynchronously.
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    dao.insert(new WAPItem(bssid, password));
                }
            });

            // Block and wait for the future to complete
//            future.get();
        } catch (Exception e) {
            Log.e("Database Insertion", e.getStackTrace().toString());
        }
        read();
    }

    /**
     * Updates the password of the existing network.
     * @param bssid The BSSID of the network.
     * @param password The password of the network.
     */
    public void update(String bssid, String password) {
        // Use asynchronous task to run query on the background to avoid locking UI
        try {
            // Run a task specified by a Runnable Object asynchronously.
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    dao.update(new WAPItem(bssid, password));
                }
            });

            // Block and wait for the future to complete
//            future.get();
        } catch (Exception e) {
            Log.e("Database Insertion", e.getStackTrace().toString());
        }
        read();
    }

    /**
     * Reads the database and cache each network configs into "waps".
     */
    private void read() {
        // Use asynchronous task to run query on the background to avoid locking UI
        try {
            // Run a task specified by a Runnable Object asynchronously.
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    waps = dao.listAll();
                }
            });

            // Block and wait for the future to complete
//            future.get();
        } catch (Exception e) {
            Log.e("Database Insertion", e.getStackTrace().toString());
        }
    }

    /**
     * Gets the cached data.
     * @return The list of cached network configs.
     */
    public List<WAPItem> getWAPs() {
        read();
        return this.waps;
    }

    /**
     * Checks if a BSSID is saved in the database.
     * @param bssid BSSID.
     * @return True/False.
     */
    public boolean contains(String bssid) {
        read();
        for (WAPItem i : waps) {
            if (i.getBssid().equals(bssid)) {
                return true;
            }
        }
        return false;
    }
}
