package com.project.swapper.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Collections;
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
        // Makes sure "waps" will never be null.
        waps = Collections.emptyList();
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
                    dao.update(bssid, password);
                }
            });

            // Block and wait for the future to complete
//            future.get();
        } catch (Exception e) {
            Log.e("Database Update", e.getStackTrace().toString());
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
            Log.e("Database ReadAll", e.getStackTrace().toString());
        }
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

    /**
     * Retrieves the password for a given BSSID.
     * @param bssid The BSSID.
     * @return The encrypted string of password.
     */
    public String getPassword(String bssid) {
        for (WAPItem i : waps) {
            if (i.getBssid().equals(bssid)) {
                return i.getPassword();
            }
        }
        return null;
    }

    /**
     * Deletes a entry from the database.
     * @param bssid The BSSID.
     */
    public void delete(String bssid) {
        // Use asynchronous task to run query on the background to avoid locking UI
        try {
            // Run a task specified by a Runnable Object asynchronously.
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    dao.delete(bssid);
                }
            });

            // Block and wait for the future to complete
//            future.get();
        } catch (Exception e) {
            Log.e("Database Deletion", e.getStackTrace().toString());
        }
        read();
    }
}
