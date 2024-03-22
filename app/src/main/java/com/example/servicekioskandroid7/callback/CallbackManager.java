package com.example.servicekioskandroid7.callback;

import android.util.Log;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CallbackManager {
    private static Map<String, Callback> callbackMap = new HashMap<>();

    private static String TAG = "CALLBACK_FLAG";

    public static void runCallback(String key, Object value) {
        try {
            Log.d(TAG, "runCallback: " + key + " " + value);
            Objects.requireNonNull(callbackMap.get(key)).execute(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void assignCallback(Callback... callbacks) {
        for (Callback callback : callbacks) {
            callbackMap.put(callback.getKey(), callback);
        }
    }

    public static void clearCallback() {
        callbackMap.clear();
    }

    public interface Callback {
        void execute(Object value) throws JSONException;

        String getKey();
    }
}
