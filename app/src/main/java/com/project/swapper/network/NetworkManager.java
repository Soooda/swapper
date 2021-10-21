package com.project.swapper.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

/**
 * This class handles join network functionality
 * Ref. https://stackoverflow.com/questions/8818290/how-do-i-connect-to-a-specific-wi-fi-network-in-android-programmatically
 */
public class NetworkManager {
    private WifiManager mWifiManager;
    private ConnectivityManager mConnectivityManager;
    private Context mContext;
    private List<ScanResult> mScanBuffer;
    // The BSSID of the current connected WAP, null if no connection
    private String currentWAP;

    public NetworkManager(Context context) {
        mContext = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mConnectivityManager = (ConnectivityManager) mContext.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        currentWAP = null;
    }

    /**
     * Switches on WIFI.
     */
    public void enableWIFI() {
        mWifiManager.setWifiEnabled(true);
    }

    /**
     * Switches off WIFI.
     */
    public void disableWIFI() {
        mWifiManager.disconnect();
        currentWAP = null;
        mWifiManager.setWifiEnabled(false);
    }

    /**
     * Attains a list of ScanResult of nearby WAPs.
     * @return Nearby WAPs.
     */
    public List<ScanResult> scan() {
        mScanBuffer = mWifiManager.getScanResults();
        return mScanBuffer;
    }

    /**
     * Checks whether the phone is currently connected to WIFI.
     * @return true/false
     */
    public boolean isConnectedToWIFI() {
        // Checks whether the phone is connecting to WIFI
        if (!mConnectivityManager.isActiveNetworkMetered()) {
            return true;
        }
        return false;
    }

    /**
     * Connects to a particular WAP given BSSID and password.
     * @param bssid The BSSID of the WAP.
     * @param password The password of the WAP.
     */
    public void add(String bssid, String password) {
        mWifiManager.disconnect();
        // Init
        WifiConfiguration conf = new WifiConfiguration();
        conf.BSSID = bssid;
        conf.status = WifiConfiguration.Status.DISABLED;
        conf.priority = 40;

        // For WPA2
        // TODO: Not sure whether it works for other methods like WEP or Open Network
        if (!password.equals("")) {
            conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        } else {
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.NONE);
        }


        if (!password.equals("")) {
            conf.preSharedKey = String.format("\"%s\"", password);
        }

        int networkId = mWifiManager.addNetwork(conf);
        if (networkId != -1) {
            mWifiManager.enableNetwork(networkId, true);
            currentWAP = bssid;
        } else {
            Log.e("NetworkManager", "Cannot connect to " + bssid);
        }
    }
}
