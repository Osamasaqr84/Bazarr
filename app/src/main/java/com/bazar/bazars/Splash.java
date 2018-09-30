package com.bazar.bazars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AG on 12/22/2016.
 */

public class Splash extends Activity {
    Handler handler;
    PreferenceHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        OneSignal.startInit(this).setNotificationOpenedHandler(new ExampleNotificationOpenedHandler()).init();
        helper = new PreferenceHelper(this);
        helper.putNotificationId("MjgwOWEwNzYtZjJkZi00MWJmLTgxMGQtZjg0Mjc2NzMxOGZl");
        try {
            sendNotification("aaaaaaaaaaaaaaa");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent in = new Intent(Splash.this, MainActivity.class);
//                startActivity(in);
//                finish();
//            }
//        }, 3000);

    }

    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
            try {
                BroadcastHelper.sendInform(Splash.this,"count");

                if (additionalData != null) {
                    if (additionalData.has("actionSelected"))
                        Log.d("OneSignalExample", "OneSignal notification button with id " + additionalData.getString("actionSelected") + " pressed");
                    Toast.makeText(Splash.this, additionalData.toString(), Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent in = new Intent(Splash.this, Notifcation_activity.class);
                            startActivity(in);
                        }
                    },1000);

                    Log.d("OneSignalExample", "Full additionalData:\n" + additionalData.toString());
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }



    public void sendNotification(String message) throws JSONException {
        JSONObject en = new JSONObject();
        en.put("en", message);
        String[] sa = new String[]{"ALL"};
        JSONObject post_dict = new JSONObject();
        try {
            post_dict.put("app_id", "fd43a290-c903-4213-bb16-23f92e77d6af");
            post_dict.put("contents", en);
            post_dict.put("included_segments", sa);
            final String requestBody = post_dict.toString();

            new Json_Controller().PostNotification(this, "https://onesignal.com/api/v1/notifications", requestBody, new VolleyCallback() {
                @Override
                public void onSuccess(String result) throws JSONException {
                Toast.makeText(Splash.this,result,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(Splash.this,error,Toast.LENGTH_LONG).show();

                }
            }, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


