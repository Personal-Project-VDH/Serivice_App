package com.example.servicekioskandroid7;
import java.net.ServerSocket;

import com.example.servicekioskandroid7.contants.Contants;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
public class ThreadTCP extends Thread {
    private String TAG = "THREAD_KIOSK";
    private ServerSocket server_tcp = null;
    public static Context context = null;

    public ThreadTCP(Context context) {
        ThreadTCP.context = context;
    }

    @Override
    public void run() {
        Log.d(TAG, "run: Threads " + this.context);
        try {
            server_tcp = new ServerTCP(Contants.SERVER_PORT);
        } catch (IOException e) {
            Log.d(TAG, "Err : Threads " + e.getMessage());

        }
    }
}
