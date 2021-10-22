package com.project.swapper.security;

public class Network {

    private String NetworkName;
    private boolean connectionStatus;
    private boolean storedInDB;
    private int NetworkStrength;

    public Network(String networkName, boolean connectionStatus, boolean storedInDB, int networkStrength) {
        NetworkName = networkName;
        this.connectionStatus = connectionStatus;
        this.storedInDB = storedInDB;
        NetworkStrength = networkStrength;
    }

    public String getNetworkName() {
        return NetworkName;
    }

    public void setNetworkName(String networkName) {
        NetworkName = networkName;
    }

    public boolean isConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(boolean connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public boolean isStoredInDB() {
        return storedInDB;
    }

    public void setStoredInDB(boolean storedInDB) {
        this.storedInDB = storedInDB;
    }

    public int getNetworkStrength() {
        return NetworkStrength;
    }

    public void setNetworkStrength(int networkStrength) {
        NetworkStrength = networkStrength;
    }
}
