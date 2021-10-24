package com.project.swapper;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.util.Log;

import com.project.swapper.database.DatabaseManager;
import com.project.swapper.network.AutoSwitchingService;
import com.project.swapper.network.AutoSwitchingServiceLowBattery;
import com.project.swapper.network.NetworkManager;
import com.project.swapper.security.EncryptionManager;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * The overall entry point of all back-end functionalities.
 */
public class Model {
    private static Model model = null;
    private Context context;
    private EncryptionManager enc;
    private NetworkManager network;
    private DatabaseManager db;

    public Model(Context context) {
        this.context = context;

        // Encryption Manager Init
        try {
            this.enc = new EncryptionManager();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            Log.e("EncryptionManager", e.getMessage());
        }

        // Network Manager Init
        this.network = new NetworkManager(context);
        // Database Manager Init
        this.db = new DatabaseManager(context);
    }

    /**
     * Creates a singleton model object.
     * @param context The Application Context.
     */
    public static void createInstance(Context context) {
        model = new Model(context);
    }

    /**
     * Modified Singleton Pattern.
     * @return The model object.
     */
    public static Model getInstance() {
        return model;
    }

    /*
    ---------------------- Service ----------------------
     */

    /**
     * Checks if AutoSwitchingService is running in the background.
     * Ref. https://stackoverflow.com/questions/600207/how-to-check-if-a-service-is-running-on-android
     * @return True/False.
     */
    public boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                Integer.MAX_VALUE)) {
            if (AutoSwitchingService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
            if (AutoSwitchingServiceLowBattery.class.getName().equals(
                    service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Starts the service.
     * @param lowBattery if running low battery mode.
     */
    public void startService(boolean lowBattery) {
        Intent intent;
        if (lowBattery) {
            intent = new Intent(context, AutoSwitchingServiceLowBattery.class);
        } else {
            intent = new Intent(context, AutoSwitchingService.class);
        }
        context.startService(intent);
    }

    /**
     * Stops the service.
     */
    public void stopService() {
        Intent intent = new Intent(context, AutoSwitchingService.class);
        context.stopService(intent);

        network.disconnect();
        network.disableWIFI();
    }

    /*
    ---------------------- Database ----------------------
     */

    /**
     * Inserts the data with encryption into the database.
     * @param bssid The bssid of the network.
     * @param password The password of the network.
     */
    public void databaseAdd(String bssid, String password) {
        try {
            password = enc.encrypt(password);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            Log.e("EncryptionManager", e.getMessage());
            return;
        }
        db.insert(bssid, password);
    }

    /**
     * Updates the password of the existing network with encryption.
     * @param bssid The BSSID of the network.
     * @param password The password of the network.
     */
    public void databaseUpdate(String bssid, String password) {
        try {
            password = enc.encrypt(password);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            Log.e("EncryptionManager", e.getMessage());
            return;
        }
        db.update(bssid, password);
    }

    /**
     * Checks if a BSSID is saved in the database.
     * @param bssid BSSID.
     * @return True/False.
     */
    public boolean databaseContains(String bssid) {
        return db.contains(bssid);
    }

    /**
     * Retrieves the password for a given BSSID.
     * @param bssid The BSSID.
     * @return The decrypted string of password or null if something goes wrong.
     */
    public String databaseGetPassword(String bssid) {
        String password = db.getPassword(bssid);
        try {
            password = enc.decrypt(password);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            Log.e("EncryptionManager", e.getMessage());
            return null;
        }
        return password;
    }

    /**
     * Deletes a entry from the database.
     * @param bssid The BSSID.
     */
    public void databaseDelete(String bssid) {
        db.delete(bssid);
    }

    /*
    ---------------------- Network ----------------------
     */

    /**
     * Switches on WIFI.
     */
    public void networkEnableWIFI() {
        network.enableWIFI();
    }

    /**
     * Switches off WIFI.
     */
    public void networkDisableWIFI() {
        network.disableWIFI();
    }

    /**
     * Attains a list of ScanResult of nearby WAPs.
     * @return Nearby WAPs.
     */
    public List<ScanResult> networkScan() {
        return network.scan();
    }

    /**
     * Checks whether the phone is currently connected to WIFI.
     * @return true/false
     */
    public boolean networkIsConnectedToWIFI() {
        return network.isConnectedToWIFI();
    }

    /**
     * Gets the BSSID of current connected WAP.
     * @return BSSID as a string.
     */
    public String networkGetCurrentBSSID() {
        return network.getCurrentBSSID();
    }

    /**
     * Gets the signal level of current connected WAP.
     * @return Signal strength as an int.
     */
    public int networkGetCurrentLevel() {
        return network.getCurrentLevel();
    }

    /**
     * Connects to a particular WAP given BSSID and password.
     * @param bssid The BSSID of the WAP.
     * @param password The password of the WAP.
     */
    public void networkConnect(String bssid, String password) {
        network.add(bssid, password);
    }

    /**
     * Disconnects from the current connection.
     */
    public void networkDisconnect() {
        network.disconnect();
    }
}
