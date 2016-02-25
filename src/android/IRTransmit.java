package com.imagenproactiva.irplugin;

import org.apache.cordova.CordovaPlugin;

import android.hardware.ConsumerIrManager;

import org.apache.cordova.CallbackContext;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.lang.String;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Runnable;

public class IRTransmit extends CordovaPlugin {
    public static final String ACTION_TRANSMIT_IR_CODE = "transmit";

    @Override
    public boolean execute(String action, JSONArray jsonArgs, final CallbackContext callbackContext) throws JSONException {
        try {
            if (ACTION_TRANSMIT_IR_CODE.equals(action)) {
                JSONObject args = jsonArgs.getJSONObject(0);
                final Integer frequency = args.getInt("frequency");
                JSONArray signalJson = args.getJSONArray("signal");
                final int[] signal = new int[signalJson.length()];
                for (int i = 0; i < signalJson.length(); ++i) {
                    signal[i] = signalJson.optInt(i);
                }
                final Context context = this.cordova.getActivity().getApplicationContext();
                this.cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        ConsumerIrManager irService = (ConsumerIrManager) context.getSystemService(context.CONSUMER_IR_SERVICE);
                        // transmit the pattern at 38.4KHz

                        if (android.os.Build.VERSION.SDK_INT == 19) {
                            int lastIdx = android.os.Build.VERSION.RELEASE.lastIndexOf(".");
                            int VERSION_MR = Integer.valueOf(android.os.Build.VERSION.RELEASE.substring(lastIdx + 1));
                            if (VERSION_MR < 3) {
                                int t = 1000000 / frequency;

                                for (int i = 0; i < signal.length; ++i) {
                                    signal[i] = signal[i] * t;
                                }
                                irService.transmit(38400, signal);
                            } else {
                                irService.transmit(38400, signal);
                            }

                        }
                        callbackContext.success("aja");
                    }
                });
            }
            return true;

        } catch (Exception e) {
            callbackContext.error("java ".concat(e.getMessage()));
            return false;
        }
    }
}

