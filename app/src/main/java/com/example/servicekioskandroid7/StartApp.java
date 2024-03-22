package com.example.servicekioskandroid7;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.servicekioskandroid7.contants.Contants;

public class StartApp {
    public static String package_name;
    public static String activityName;

    public static void startNewActivity(Context context, String package_name) {
        StartApp.package_name = package_name;
        StartApp.activityName = package_name + ".MainActivity";
        try {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(package_name);
            Log.d("START_APP", "startNewActivity " + launchIntent);
            Log.d("START_APP", "ServerTCP.socket " + ServerTCP.socket);
            ThreadStartApp.count = Contants.OPEN;


            if (launchIntent != null) {
                ThreadStartApp.count = Contants.CLOSE;
                ServerTCP.socket.close();
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                launchIntent.setClassName(package_name, activityName);
                context.startActivity(launchIntent);//null pointer check in case package name was not found
            }
        } catch (Exception e) {
            Log.d("START_APP", "err start app" + e);
            Log.d("START_APP", "err start app" + context);
            ThreadStartApp.count = Contants.OPEN;
            ThreadStartApp.package_name = "";
        }

    }
}
