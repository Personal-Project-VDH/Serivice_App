package com.example.servicekioskandroid7;

import com.example.servicekioskandroid7.contants.Contants;
import android.util.Log;
public class ThreadStartApp extends Thread {
    public static String package_name;
    public static int count = Contants.CLOSE;
    private String TAG = "THREAD_KIOSK";
    private int DEFAULT_TIMEOUT = 5;
    private int DEFAULT_TIMEOUT_CLOSE = 15;

    @Override
    public void run() {
        try {
            int count_close = 0;
            while (Thread.currentThread().isAlive()) {
                Log.d(TAG, "run: " + count);
                if (count == Contants.QUIT) {
                    Thread.sleep(1000);
                    continue;
                } else if (count == Contants.CLOSE) {
                    if (count_close == ThreadStartApp.this.DEFAULT_TIMEOUT_CLOSE) {
                        count_close = 0;
                        StartApp.startNewActivity(ThreadTCP.context, ThreadStartApp.package_name);
                    } else {
                        count_close++;
                    }

                    Thread.sleep(1000);
                    continue;
                }
                ThreadStartApp.count++;
                Thread.sleep(1000);
                if (ThreadStartApp.count >= ThreadStartApp.this.DEFAULT_TIMEOUT) {
                    StartApp.startNewActivity(ThreadTCP.context, ThreadStartApp.package_name);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
