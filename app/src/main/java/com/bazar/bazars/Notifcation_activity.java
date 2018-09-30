package com.bazar.bazars;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.bazar.bazars.Adapters.notifcation_adapter;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.notification_items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tournedo2003 on 3/31/17.
 */

public class Notifcation_activity extends AppCompatActivity {
private ArrayList<notification_items> array;
    public static notifcation_adapter adapter ;
    public ListView myads;
    private String data,post_id ;
    ImageButton backbtn;
    PreferenceHelper helper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifcation);
        helper = new PreferenceHelper(this);
        myads = (ListView) findViewById(R.id.notif);
        backbtn=(ImageButton)findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showSimpleProgressDialog(this,"",getString(R.string.pleasewait),false);
        trans();
    }
    String notifiable_id;
    public void trans() {
        JSONObject object = new JSONObject();
        try{
            object.put("userId",Integer.parseInt(helper.getUserID()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestbody = object.toString();
        new Json_Controller().PostData2Server(this, "http://alsog.net/api/notifcations/mynot"
                , requestbody, new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) throws JSONException {
                        removeSimpleProgressDialog();
                        if (!result.equals("")) {
                            array = new ArrayList<notification_items>();
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                JSONArray main_obj = jsonObject.getJSONArray("data");
                                for (int index = 0; index < main_obj.length(); index++) {
                                    JSONObject catgrey = main_obj.getJSONObject(index);
                                    String id = catgrey.getString("id");
                                    notifiable_id = catgrey.getString("id");
                                    String modified_at = catgrey.getString("modified");
                                    String read_at = catgrey.getString("seen");
                                    String created_id = catgrey.getString("created").replace("T", " ");
                                   // JSONObject catsub = catgrey.getJSONObject("advertise");
                                   data = catgrey.getString("comment");
                                    post_id = catgrey.getString("advertise_id");
                                    array.add(new notification_items(id, "s", notifiable_id, "s", data,created_id, modified_at, read_at, post_id));
                                }
                                Collections.reverse(array);
                                adapter = new notifcation_adapter(Notifcation_activity.this , array) ;
                                myads.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            removeSimpleProgressDialog();
                            Toast.makeText(Notifcation_activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        removeSimpleProgressDialog();
                        Toast.makeText(Notifcation_activity.this, "" + error, Toast.LENGTH_SHORT).show();
                        Toast.makeText(Notifcation_activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                },false);




    }
    private static ProgressDialog mProgressDialog;

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void openNotification(String post_id,String data) throws JSONException {
//        JSONObject object = new JSONObject();
//        object.put("comment",data);
//        object.put("advertise_id",data);
//        object.put("user_id",helper.getUserID());

      //  final String requestbody = object.toString();
        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);

                JSONObject elhay = jsonObject.getJSONObject("elhay");
                if(elhay.getInt("seen") == 1){
                    array.get(pos).setRead_at("1");
                    //  Toast.makeText(Notifcation_activity.this,getString(R.string.day), Toast.LENGTH_SHORT).show();
                }
                BroadcastHelper.sendInform(Notifcation_activity.this,"aliwo");
            }

            @Override
            public void onError(String error) {
                Toast.makeText(Notifcation_activity.this,getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
            }
        },this, "http://alsog.net/api/notifcations/seen/"+post_id
                , false);
    }

    Receiver receivera;
    boolean isRecieverRegistered  = false;
    @Override
    protected void onResume() {
        super.onResume();
        if (receivera == null) {
            receivera = new Receiver();
            IntentFilter filter = new IntentFilter(BroadcastHelper.ACTION_NAME);
            registerReceiver(receivera, filter);
            isRecieverRegistered = true;
        }
        if(adapter != null)
        adapter.notifyDataSetChanged();


    }

    @Override
    protected void onDestroy() {
        if(isRecieverRegistered){
            unregisterReceiver(receivera);
        }
        super.onDestroy();
    }

    int pos = 0;
    private class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            //   Log.v("r", "receive " + arg1.getStringExtra(BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME));
            String methodName = arg1.getStringExtra(BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME);
            if (methodName != null && methodName.length() > 0) {
                //  Log.v("receive", methodName);
                switch (methodName) {
                    case "openno":
                        pos = arg1.getIntExtra("pos",0);
                        try {
                            openNotification(array.get(pos).getNotifiable_id(),array.get(pos).getPost_id());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "aliwo":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                         Intent intent = new Intent(Notifcation_activity.this,ADetailed.class);
                                        intent.putExtra("id_of_row",array.get(pos).getPost_id());
                                        startActivity(intent);

                            }
                        });
                       
                        break;
                }
            }}
    }
}
