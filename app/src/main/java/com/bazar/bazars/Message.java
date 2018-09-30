package com.bazar.bazars;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bazar.bazars.Adapters.Message_Adapter;
import com.bazar.bazars.Items.Message_Item;
import com.bazar.bazars.Items.Message_Itema;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by AG on 4/2/2017.
 */

public class Message extends Activity implements Response.ErrorListener, AsyncTaskCompleteListener, View.OnClickListener {

    private ListView message_list;
    private ArrayList<Message_Itema> newarray;
    private ArrayList<Message_Item> arrayList;
    private Message_Adapter adapter;
    private TextView title, sender, time;
    Timer timer;
    ImageButton backbtn;
    private RequestQueue requestQueue;
    PreferenceHelper helper;
    int ia = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        helper = new PreferenceHelper(this);
        message_list = (ListView) findViewById(R.id.message_listview);

        title = (TextView) findViewById(R.id.mess_title);
        sender = (TextView) findViewById(R.id.sender_mess);
        time = (TextView) findViewById(R.id.time_mess);
        requestQueue = Volley.newRequestQueue(this);
        backbtn = (ImageButton) findViewById(R.id.message_backbtn);
        backbtn.setOnClickListener(this);
        arrayList = new ArrayList<Message_Item>();
        newarray = new ArrayList<Message_Itema>();
        Collections.reverse(arrayList);
        adapter = new Message_Adapter(arrayList, this);
        message_list.setAdapter(adapter);
        showSimpleProgressDialog(this, "", getString(R.string.pleasewait), false);

        startUpdateMesssage();

        message_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Details_Message.class);
             //   for (int a =0;a<arrayList.size();a++) {
                    if(arrayList.get(i).getFrom()!= Integer.parseInt(helper.getUserID())){
                        ia = arrayList.get(i).getFrom();
                        intent.putExtra("id_of_user",ia );
                       // break;
                    } else {
                        ia = arrayList.get(i).getTo();
                        intent.putExtra("id_of_user",ia );
                    }
              //  }
                for (int a =0;a<arrayList.size();a++) {
                    if(!arrayList.get(a).getSender_name().equals(helper.getUserName())) {
                        intent.putExtra("name_of_chat", arrayList.get(i).getSender_name());
                        break;
                    }
                }
                intent.putExtra("message_title", arrayList.get(i).getMessage_title());
                startActivity(intent);

            }
        });
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



    private void getMesseges() {
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Accept", "application/json");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("url", "http://alsog.net/api/messages/myChat.json");
        map.put("user_id", helper.getUserID());
        // new HttpRequester(activity, map,
        // Const.ServiceCode.GET_REQUEST_STATUS,
        // true, this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                20, this, this, header));
    }



    private void startUpdateMesssage() {
        getMesseges();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerRequestStatus(), 5 * 1000, 5 * 1000);
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        super.onDestroy();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        removeSimpleProgressDialog();
        Toast.makeText(this, getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {

            case 20:
                removeSimpleProgressDialog();
                try {
                    if (response != null) {
                        arrayList.clear();
                        String user_id_json = null;
                        JSONObject main_obj = new JSONObject(response);

                        JSONArray experts = main_obj.getJSONArray("myChat");
                        for (int i = 0; i < experts.length(); i++) {
                            Message_Item item = new Message_Item();
                            JSONObject obj = experts.getJSONObject(i);
                            try {
                                item.setFrom(obj.getInt("fromm"));
                                item.setTo(obj.getInt("too"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            item.setTime_send(obj.getString("modified").replace("T", " "));

                            JSONArray chatarray = obj.getJSONArray("chats");
                            newarray = new ArrayList<>();
                            for (int k = 0; k < chatarray.length(); k++) {
                                Message_Itema message_item = new Message_Itema();
                                JSONObject a = chatarray.getJSONObject(k);
                                message_item.setTo(a.getInt("too"));
                                message_item.setFrom(a.getInt("fromm"));
                                message_item.setMessage_title(a.getString("msgs"));
                                message_item.setIsReaded(a.getString("seen"));
                                if(a.getString("username").equals(helper.getUserName())) {
                                    message_item.setSender_name(a.getString("userto"));
                                } else {
                                    message_item.setSender_name(a.getString("username"));
                                }

                                newarray.add(message_item);
                            }
                            item.setCode_id(obj.getString("id"));
                            item.setMesseges(newarray);
                            if(newarray.size() >0) {
                                item.setSender_name(newarray.get(newarray.size()-1).getSender_name());
                                item.setMessage_title(newarray.get(newarray.size()-1).getMessage_title());
                            }
                            arrayList.add(item);
                        }
                        adapter.notifyDataSetChanged();
                        removeSimpleProgressDialog();
                    } else {
                        Toast.makeText(Message.this, "برجاء اعادة التحميل", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_backbtn:
                finish();
                break;
        }
    }

    private class TimerRequestStatus extends TimerTask {

        @Override
        public void run() {
            getMesseges();
        }
    }
}



