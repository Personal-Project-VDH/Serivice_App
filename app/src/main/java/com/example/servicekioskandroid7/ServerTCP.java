package com.example.servicekioskandroid7;



import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;


import com.example.servicekioskandroid7.callback.CallbackManager;

import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerTCP extends ServerSocket {

    public static Socket socket;
    private final String TAG = "TCP_VDH";
    private boolean isRunning;


    public ServerTCP(int port) throws IOException {
        super(port);
        Log.d(TAG, "Start TCP: " + port);
        ThreadStartApp start_app = new ThreadStartApp();
        start_app.start();
        this.onData();

    }

    public void stopServerSocket() {
        this.isRunning = false;
        try {
            this.close();
            ServerTCP.socket.close();
            ServerTCP.socket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onData() throws IOException {
        try {
            this.isRunning = true;
            StringBuilder sb = new StringBuilder();
            while (isRunning) {
                if (ServerTCP.socket != null) {
                    ServerTCP.socket.close();
                }
                ServerTCP.socket = this.accept();
                Log.d(TAG, "onData: acppected connect");
                InputStream read_data = ServerTCP.socket.getInputStream();
                int ch = 0;
                while (ch != -1) {
                    ch = read_data.read(); // Receive data from client
                    if (ch == (byte) 0x7F) {
                        sb.delete(0, sb.length());
                    } else if (ch == (byte) 0x7E) {
                        String result = sb.toString();
                        JSONObject jObj = new JSONObject(result);
                        String key = jObj.getString("CMD");
                        CallbackManager.runCallback(key, jObj);
                    } else {
                        sb.append((char) (ch & 0xFF));
                    }

                }
            }
        } catch (IOException e) {
            this.isRunning = false;
            this.onData();
            Log.d(TAG, "onData: IOException " + e);
        } catch (Exception e) {

            Log.d(TAG, "onData: Exception " + e);
        }
    }


}


class IPV4 {
    public static String getIP(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            // Địa chỉ IP có thể được biểu diễn dưới dạng dấu chấm thập phân, ví dụ: "192.168.1.100"
            @SuppressLint("DefaultLocale") String ip = String.format("%d.%d.%d.%d",
                    (ipAddress & 0xff),
                    (ipAddress >> 8 & 0xff),
                    (ipAddress >> 16 & 0xff),
                    (ipAddress >> 24 & 0xff));
            return ip;
        } else {
            Log.e("NetworkUtils", "Wifi is not enabled");
            return null;
        }
    }
}