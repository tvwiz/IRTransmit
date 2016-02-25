package com.bhargavakumark.irtransmit;

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
    public static final String ACTION_TRANSMIT = "transmit";
    public static final String ACTION_HASIREMITTER = "hasIrEmitter";
    public static final String ACTION_GETCARRIERFREQUENCIES = "getCarrierFrequencies";

    public void executeTransmit(JSONArray jsonArgs, final CallbackContext callbackContext) throws JSONException {
        try {
            JSONObject args = jsonArgs.getJSONObject(0);
            final Integer frequency = args.getInt("frequency");
            JSONArray patternJson = args.getJSONArray("pattern");
            final int[] pattern = new int[patternJson.length()];
            for (int i = 0; i < patternJson.length(); ++i)
                pattern[i] = patternJson.optInt(i);

            final Context context = this.cordova.getActivity().getApplicationContext();
            this.cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    ConsumerIrManager irService = (ConsumerIrManager) context.getSystemService(context.CONSUMER_IR_SERVICE);

                    if (android.os.Build.VERSION.SDK_INT == 19) {
                        int lastIdx = android.os.Build.VERSION.RELEASE.lastIndexOf(".");
                        int VERSION_MR = Integer.valueOf(android.os.Build.VERSION.RELEASE.substring(lastIdx + 1));
                        if (VERSION_MR < 3) {
                            int t = 1000000 / frequency;
                            for (int i = 0; i < pattern.length; ++i)
                                pattern[i] = pattern[i] * t;
                        }
                        // transmit the pattern at 38.4KHz
                        //irService.transmit(38400, pattern);
                        irService.transmit(frequency, pattern);

                    }
                    callbackContext.success();
                }
            });
        } catch (Exception e) {
            callbackContext.error("java ".concat(e.getMessage()));
        }
    }

    public void executeHasIrEmitter(JSONArray jsonArgs, final CallbackContext callbackContext) throws JSONException {
        final Context context = this.cordova.getActivity().getApplicationContext();

        this.cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                if (android.os.Build.VERSION.SDK_INT < 19) {
                    Log.d("IRTransmit", "SDK version does not support IR transmit service " + android.os.Build.VERSION.SDK_INT);
                    callbackContext.success("false");
                    return;
                }

                ConsumerIrManager irService = (ConsumerIrManager) context.getSystemService(context.CONSUMER_IR_SERVICE);
                if (irService == null) {
                    Log.d("IRTransmit", "CONSUMER_IR_SERVICE not found");
                    callbackContext.success("false");
                    return;
                }

                if (irService.hasIrEmitter()) {
                    Log.d("IRTransmit", "IR Emitter/Transmitter found");
                    callbackContext.success("true");
                } else {
                    Log.d("IRTransmit", "NO IR Emitter/Transmitter");
                    callbackContext.success("false");
                }
            }
        });
    }

    @Override
    public boolean execute(String action, JSONArray jsonArgs, final CallbackContext callbackContext) throws JSONException {
        if (ACTION_TRANSMIT.equals(action)) {
            executeTransmit(jsonArgs, callbackContext);
            return true;
        } else if (ACTION_HASIREMITTER.equals(action)) {
            executeHasIrEmitter(jsonArgs, callbackContext);
            return true;
        } else {
            return false; // Returning false results in a "MethodNotFound" error.
        }
    }
}

