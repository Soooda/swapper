package com.project.swapper.network;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.project.swapper.Model;
import com.project.swapper.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AutoSwitchingServiceLowBattery extends Service {
    private static final int ID_SERVICE = 102;
    private final long DELAY = 200;
    private final long INTERVAL = 5000;
    private final int THRESHOLD = 5; // Switching threshold

    private Model model;
    private Timer timer;

    /**
     * The task done by this service.
     */
    private void task() {
        List<ScanResult> waps = model.networkScan();

        // If not connected to WIFI, stop
        if (!model.networkIsConnectedToWIFI()) {
            Log.e("AutoSwitchingService", "WIFI is not enabled!");
            stopSelf();
        }

        String currentBSSID = model.networkGetCurrentBSSID();
        int currentLevel = model.networkGetCurrentLevel();

        String target = null; // The BSSID of the connection that will be switched to.
        int level = currentLevel + THRESHOLD;

        // ScanResult traversal
        for (ScanResult s : waps) {
            // Ignore current connection
            if (s.BSSID.equals(currentBSSID)) {
                continue;
            }

            // The connection is not in the database, ignore!
            if (!model.databaseContains(s.BSSID)) {
                continue;
            }
            // Check if the signal is better than the current connection
            if (s.level > level) {
                level = s.level;
                target = s.BSSID;
            }
        }

        // Switch
        if (target != null) {
            model.networkConnect(target, model.databaseGetPassword(target));
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Ref. https://stackoverflow.com/questions/17062523/how-to-place-a-thread-with-and-infinite-foor-loop-inside-a-background-service
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        task();
                    }
                },
                DELAY,
                INTERVAL
        );

        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();

        // Create the Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String NOTIFICATION_CHANNEL_ID = "com.project.swapper";
            String channelName = "sWAPper";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName,
                    NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getApplicationContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                    this, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.mipmap.logo_foreground)
                    .setContentTitle("sWAPper is running!")
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
            startForeground(ID_SERVICE, notification);
        } else {
            startForeground(ID_SERVICE, new Notification());
        }

        this.model = Model.getInstance();
        if (this.model == null) {
            Log.e("AutoSwitchingService", "Unable to get Model instance, service shuts down!");
            stopSelf();
        }

        Log.i("AutoSwitchingService", "Service Spawned!");
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
        Log.i("AutoSwitchingService", "Service Terminated!");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
