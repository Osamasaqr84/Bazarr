package com.bazar.bazars;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.onesignal.OneSignal;

import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    PreferenceHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        OneSignal.startInit(this).setNotificationOpenedHandler(new ExampleNotificationOpenedHandler()).init();

        helper = new PreferenceHelper(this);
        helper.putNotificationId("NDk0ODY5YTYtM2U0Ni00NzM3LTlmMzAtOGY0OTUxMWMwM2U4");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(in);
                finish();
            }
        },3000);
    }


    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
            try {
                BroadcastHelper.sendInform(SplashActivity.this,"count");

                if (additionalData != null) {
                    if (additionalData.has("actionSelected"))
                        Log.d("OneSignalExample", "OneSignal notification button with id " + additionalData.getString("actionSelected") + " pressed");
                    Toast.makeText(SplashActivity.this, additionalData.toString(), Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent in = new Intent(SplashActivity.this, Notifcation_activity.class);
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
}
