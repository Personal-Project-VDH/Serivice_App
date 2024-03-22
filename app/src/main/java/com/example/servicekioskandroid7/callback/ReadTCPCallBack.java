package com.example.servicekioskandroid7.callback;


import android.util.Log;

import com.example.servicekioskandroid7.ServerTCP;
import com.example.servicekioskandroid7.ThreadStartApp;
import com.example.servicekioskandroid7.contants.Contants;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadTCPCallBack {

    public static void startAppMethod() {
        CallbackManager.assignCallback(
                new CallbackManager.Callback() {
                    @Override
                    public void execute(Object value) throws JSONException {

                        JSONObject jObj = new JSONObject(value.toString());
                        String package_name = jObj.getString("NAME");
                        int status = Integer.parseInt(jObj.getString("STATUS"));
                        if (status == Contants.OPEN) {
                            ThreadStartApp.package_name = package_name;
                            ThreadStartApp.count = Contants.OPEN;
                        } else if (status == Contants.QUIT) {
                            ThreadStartApp.count = Contants.QUIT;
                        } else {
                            ThreadStartApp.count = Contants.CLOSE;
                        }

                    }

                    @Override
                    public String getKey() {
                        return "PO";
                    }
                }
        );
    }
}
