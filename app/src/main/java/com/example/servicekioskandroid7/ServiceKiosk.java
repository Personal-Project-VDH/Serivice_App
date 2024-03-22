package com.example.servicekioskandroid7;

import android.app.Service;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ServiceKiosk extends Service {
    private ThreadTCP create_server = null;

    private Context context = null;

    private String TAG = "SERVICE_TAG";


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint({"ForegroundServiceType"})
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.context = getApplicationContext();
        Log.d(TAG, "Service onStartCommand " + this.context);
        create_server = new ThreadTCP(this.context);
        create_server.start();
        final String CHANEL_ID = "Foreground Service";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANEL_ID, CHANEL_ID, NotificationManager.IMPORTANCE_LOW);

            getSystemService(NotificationManager.class).createNotificationChannel(channel);
            Notification.Builder notification = new Notification.Builder(this, CHANEL_ID).setContentText("Service is running!!").setContentTitle("My Service");
            startForeground(1001, notification.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Service App")
                    .setContentText("Service is running!")
                    .setPriority(NotificationCompat.PRIORITY_LOW);
            startForeground(1001, builder.build());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service stop");
    }
}
