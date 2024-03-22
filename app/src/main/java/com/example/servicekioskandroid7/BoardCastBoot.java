package com.example.servicekioskandroid7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.servicekioskandroid7.callback.ReadTCPCallBack;

import java.util.Objects;

public class BoardCastBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), Intent.ACTION_BOOT_COMPLETED)) {
            Intent service = new Intent(context, ServiceKiosk.class);
            context.startService(service);
            Log.d("BOOT_SERVICE", "onReceive: boot complete");
            ReadTCPCallBack.startAppMethod();
        }
    }
}