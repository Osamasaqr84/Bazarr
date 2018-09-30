package com.bazar.bazars;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bazar.bazars.Adapters.Chat_Adapter;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.Chat_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

/**
 * Created by AG on 4/10/2017.
 */

public class Details_Message extends Activity implements Response.ErrorListener, AsyncTaskCompleteListener, View.OnClickListener {
    TextView postition, name_chat;
    private RecyclerView chat_list;
    private ArrayList<Chat_item> arrayList;
    private ArrayList<Chat_item> arrayListorg;
    private Chat_Adapter adapter;
    private TextView sendername;
    PreferenceHelper helper;
    private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();
    int id_from,codddde,sender,idto;
    Timer timer;
    private RequestQueue requestQueue;
    EditText message;
    ImageButton send,backk;
    int messageStatues;
    LinearLayoutManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_message);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        helper = new PreferenceHelper(this);

        name_chat = (TextView) findViewById(R.id.name_of_chat);
        backk = (ImageButton)findViewById(R.id.backk);
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chat_list = (RecyclerView) findViewById(R.id.chat_message_listview);
        message = (EditText)findViewById(R.id.messsage_edt) ;
        arrayList = new ArrayList<Chat_item>();
        arrayListorg = new ArrayList<Chat_item>();
        sendername = (TextView)findViewById(R.id.sender_name);
        Intent intent = getIntent();
        String s = intent.getStringExtra("name_of_chat");
        codddde = intent.getIntExtra("id_of_user",0);
        sendername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Details_Message.this,MyADS.class);
                intent1.putExtra("userid",String.valueOf(codddde));
                startActivity(intent1);
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        send = (ImageButton)findViewById(R.id.send_newmessage);
        send.setOnClickListener(this);
        showSimpleProgressDialog(this,"",getString(R.string.pleasewait),false);


        // sender = intent.getIntExtra("mein",0);
        sendername.setText(s);
        //name_chat.setText(sender);
         manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        chat_list.setLayoutManager(manager);
        chat_list.setHasFixedSize(true);
        adapter = new Chat_Adapter(arrayList, this);
        chat_list.setAdapter(adapter);

        startUpdateMesssage();
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
        header.put("Accept","application/json");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("url", "http://alsog.net/api/messages/chatBTWusers");
        map.put("fromm",helper.getUserID());
        map.put("too",String.valueOf(codddde));

        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                12, this, this,header));
    }

    @Override
    protected void onDestroy() {
        if(timer != null){
            timer.cancel();
            timer.purge();
            timer = null;
        }
        super.onDestroy();
    }


    private void sendMessage(String message) {
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Accept","application/json");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL,
                "http://alsog.net/api/chats/add.json");
        map.put("too", String.valueOf(codddde));
        map.put("fromm", helper.getUserID());
        map.put("msgs", message);
        map.put("username", helper.getUserName());
        map.put("userto", sendername.getText().toString());

        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                Const.ServiceCode.SEND_MESSAGE, this, this,header));
        try {
            sendNotification(getString(R.string.newmessages));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void startUpdateMesssage() {
        getMesseges();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerRequestStatus(), 6*1000, 6*1000);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
    removeSimpleProgressDialog();
        Toast.makeText(this, String.valueOf(error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode){
            case 12:
                arrayList.clear();
                removeSimpleProgressDialog();
                try {
                    if (response != null) {
                        JSONObject main_obj = new JSONObject(response);
                        JSONArray expertss = main_obj.getJSONArray("myChat");
                        JSONArray experts = expertss.getJSONObject(0).getJSONArray("chats");
                        for (int i = 0; i < experts.length(); i++) {
                            Chat_item item = new Chat_item();
                            JSONObject obj = experts.getJSONObject(i);
                            String chat_json = obj.getString("msgs");
                            String message_time= obj.getString("modified").replace("T"," ");
                             id_from = obj.getInt("fromm");
                            idto = obj.getInt("too");

                             item.setChat_right(chat_json);
                            item.setMessage_time(message_time);
                            if (id_from==Integer.parseInt(helper.getUserID())) {
                                item.setMine(true);
                            } else {
                                 item.setMine(false);
                            }
                            arrayList.add(item);
                        }
                        removeSimpleProgressDialog();
                        manager.setStackFromEnd(true);
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(Details_Message.this, "برجاء اعادة التحميل", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Const.ServiceCode.SEND_MESSAGE:
             //   try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    messageStatues = Integer.parseInt(jsonObject.getString("status"));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                if (messageStatues == 1) {
                    Toast.makeText(this, getString(R.string.messagesent), Toast.LENGTH_SHORT).show();
                    getMesseges();
                    chat_list.scrollToPosition(arrayList.size()-1);
                    message.setText("");

//
//                } else {
//                    Toast.makeText(this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//                }
                break;
        }
    }
    public void sendNotification(String message) throws JSONException {

        JSONArray tags = new JSONArray();
        JSONObject filters = new JSONObject();
        filters.put("field", "tag");
        filters.put("key", "id");
        filters.put("relation", "=");
        filters.put("value",String.valueOf(codddde));
        tags.put(filters);
        JSONObject en = new JSONObject();
        en.put("en", message);
        JSONObject foo = new JSONObject();
        en.put("foo", "bar");
        // String[] sa = {["ALL"]};
        JSONObject post_dict = new JSONObject();
        try {
            post_dict.put("app_id", "fd43a290-c903-4213-bb16-23f92e77d6af");
            post_dict.put("included_segments", "All");
            post_dict.put("data", foo);
            post_dict.put("contents", en);
            post_dict.put("tags", tags);

            final String requestBody = post_dict.toString();

            new Json_Controller().PostNotification(this, "https://onesignal.com/api/v1/notifications", requestBody, new VolleyCallback() {
                @Override
                public void onSuccess(String result) throws JSONException {
                    Toast.makeText(Details_Message.this, result, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(Details_Message.this, error, Toast.LENGTH_LONG).show();
                    removeSimpleProgressDialog();
                }
            }, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_newmessage:
                sendMessage(message.getText().toString());
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
