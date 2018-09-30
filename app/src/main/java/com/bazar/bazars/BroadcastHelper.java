package com.bazar.bazars;


import android.content.Context;
import android.content.Intent;


public class BroadcastHelper {
      static final String BROADCAST_EXTRA_METHOD_NAME = "INPUT_METHOD_CHANGED";
      static final String ACTION_NAME = "com.bazar.codesharaj";
    private static final String UPDATE_LOCATION_METHOD = "updateLocation";


    public static void sendInform(Context context, String method) {
        Intent intent = new Intent();
        intent.setAction(ACTION_NAME);
        intent.putExtra(BROADCAST_EXTRA_METHOD_NAME, method);
        try {
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendInform(Context context, String method, Intent intent) {
        intent.setAction(ACTION_NAME);
        intent.putExtra(BROADCAST_EXTRA_METHOD_NAME, method);
        try {
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void sendUpdateLocation(Context context, UpdateLocationViewModel updateLocationViewModel){
        Intent intent = new Intent();
        intent.setAction(BROADCAST_EXTRA_METHOD_NAME);
        intent.putExtra(BROADCAST_EXTRA_METHOD_NAME, UPDATE_LOCATION_METHOD);
        intent.putExtra(UPDATE_LOCATION_METHOD,updateLocationViewModel);
        try {
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
