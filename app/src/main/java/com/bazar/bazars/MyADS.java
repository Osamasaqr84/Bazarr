package com.bazar.bazars;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bazar.bazars.Adapters.myads_adapter;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.Cities_items;
import com.bazar.bazars.Items.Container_items;
import com.bazar.bazars.Items.User_info;
import com.bazar.bazars.Items.User_items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by tournedo2003 on 3/28/17.
 */

public  class MyADS extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener, AsyncTaskCompleteListener {
 public RecyclerView myads;
    public  myads_adapter adapter ;
    public ArrayList<Container_items> Cat_arrayList;
    public ArrayList<Cities_items> City_array;
    public ArrayList<User_items> user_array;
    String subids,itemid;
    Container_items items;
    ImageButton backbtn,delete_favbtn;
    RelativeLayout toolbar;
    PreferenceHelper helper;
    ImageView checkmark;
    TextView evalutae_user,user_name,online,special_user,created_at;
    boolean d;
    View view;
     ArrayList<Container_items> selectedAds = new ArrayList();
    ArrayList<String> ids = new ArrayList<>();
    User_info info;
    String subnames;
    LinearLayoutManager manager;
    int pastVisiblesItems , visibleItemCount, totalItemCount,messageStatues =0;
    private boolean loading = true;
    String linkk = null;
    LinearLayout evaluate;
    private RequestQueue requestQueue;
    String userid,adid;
    LinearLayout messagelin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myads);
        backbtn = (ImageButton)findViewById(R.id.backbtn);
        delete_favbtn = (ImageButton)findViewById(R.id.delete_fav);
        backbtn.setOnClickListener(this);
        delete_favbtn.setOnClickListener(this);
        messagelin = (LinearLayout)findViewById(R.id.messageLinear);
        messagelin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMessageDialog();
            }
        });
        //delete_favbtn.setClickable(false);
        helper = new PreferenceHelper(this);
        ids=new ArrayList<String>();
        requestQueue = Volley.newRequestQueue(this);
        info = new User_info();
        view = (View)findViewById(R.id.listitemm);
        itemid = "";
        evaluate = (LinearLayout)findViewById(R.id.linevaluate);
        evaluate.setOnClickListener(this);
        evalutae_user = (TextView)findViewById(R.id.evalutae_user);
        user_name = (TextView)findViewById(R.id.user_name);
        online = (TextView)findViewById(R.id.online);
        special_user = (TextView)findViewById(R.id.special_user);
        checkmark = (ImageView)findViewById(R.id.check_mark);
        created_at = (TextView)findViewById(R.id.created_at) ;
        Intent intent = getIntent();
        Cat_arrayList = new ArrayList<Container_items>();
        toolbar = (RelativeLayout)findViewById(R.id.radioGroupParent);
         userid = intent.getStringExtra("userid");
        if(intent.getStringExtra("adid")!= null){
            adid = intent.getStringExtra("adid");
        }
        trans(userid);
        userInfo(userid);
        myads = (RecyclerView) findViewById(R.id.ads_listView);
         manager = new LinearLayoutManager(MyADS.this);
        myads.setHasFixedSize(true);
        myads.setLayoutManager(manager);
        myads.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = manager.getChildCount();
                    totalItemCount = manager.getItemCount();
                    pastVisiblesItems = manager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 13) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
//                            if (linkk.length() > 4) {
//                             //   transNext(linkk);
//                            }
                        }
                    }
                }
            }
        });

        // Capture ListView item click


        //Log.i("array", String.valueOf(link));
    }
    private static ProgressDialog mProgressDialog;
    private void openMessageDialog() {
        final Dialog mDialog = new Dialog(this, R.style.MyDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.message_dialog);

        final EditText tvTitle = (EditText) mDialog.findViewById(R.id.message_content);
        TextView titlee = (TextView)mDialog.findViewById(R.id.message_title);
        titlee.setText(getString(R.string.private_message) + " " + info.getName());
        Button btnCancel = (Button) mDialog.findViewById(R.id.cancel_send);
        final ImageButton close = (ImageButton)mDialog.findViewById(R.id.closedialog);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        Button btnSend = (Button) mDialog.findViewById(R.id.send_message_btn);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = tvTitle.getText().toString();
                sendMessage(message);
                mDialog.dismiss();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDialog.dismiss();
            }
        });
        mDialog.show();

    }

//    private void sendMessage(String message) {
//        HashMap<String, String> header = new HashMap<String, String>();
//        header.put("Accept","application/json");
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put(Const.URL,
//                "http://alsog.net/api/messages/add.json");
//        map.put("advertise_id", adid);
//        map.put("user_id", helper.getUserID());
//        map.put("post", message);
//        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
//                Const.ServiceCode.SEND_MESSAGE, this, this,header));
//    }

    private void sendMessage(String message) {
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Accept","application/json");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL,
                "http://alsog.net/api/chats/add.json");
        map.put("too", userid);
        map.put("fromm", helper.getUserID());
        map.put("msgs", message);
        map.put("username", helper.getUserName());
        map.put("userto", user_name.getText().toString());

        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                Const.ServiceCode.SEND_MESSAGE, this, this,header));
    }


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
    public void userInfo(String userid) {
        showSimpleProgressDialog(this,"",getString(R.string.pleasewait),false);
        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                    try {

                        JSONObject main_obj = new JSONObject(result);
                        JSONObject priceass = main_obj.getJSONObject("user");
                        info.setName(priceass.getString("username"));
                        try {
                            if(priceass.getString("info")!= null) {
                                info.setInfo(priceass.getString("info"));
                            } else {
                                info.setInfo("0");
                            }
                        } catch (JSONException e){
                            e.printStackTrace();

                        }

                        info.setOnline(priceass.getInt("state"));
                        if(priceass.getInt("state")==0){
                                 online.setText(getString(R.string.offline));
                            }else {
                                online.setText(getString(R.string.online));

                        }

                            }  catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(info!=null){
                                user_name.setText(info.getName());
                                created_at.setText(getTime(info.getCreated_at()));
                                    if(info.getInfo()!= null)
                                     if(Integer.parseInt(info.getInfo())>=2){
                                        special_user.setVisibility(View.VISIBLE);
                                        checkmark.setVisibility(View.VISIBLE);
                                    }


                            }
                } else {
                    Toast.makeText(MyADS.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MyADS.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(MyADS.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, MyADS.this, "http://alsog.net/api/users/views/"+userid, false);


    }

    public String getTime(String adTime){

        String myStrDate = adTime;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String finaltime= "";
        int days,hours,min;
        try {
            if(myStrDate!=null) {
                Date date = format.parse(myStrDate);
                Date cdate = new Date(System.currentTimeMillis());

                long difference = cdate.getTime() - date.getTime();
                days = (int) (difference / (1000 * 60 * 60 * 24));
                hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60*100));
                min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
                if (days > 0) {
                    finaltime = (this.getString(R.string.before) + " " + days + " " + this.getString(R.string.day) + " " + this.getString(R.string.and) + " " + hours + " " + this.getString(R.string.hour));
                } else if (hours > 0) {
                    finaltime=(this.getString(R.string.before) + " " + hours + " " + this.getString(R.string.hour) + " " + this.getString(R.string.and) + " " + min + " " + this.getString(R.string.min));
                } else {
                    finaltime=(this.getString(R.string.before) + " " + min + " " + this.getString(R.string.min));
                }
            }
            //Toast.makeText(activity,String.valueOf("days" +days+ "hours"+ hours +"min" + min),Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return finaltime;
    }


    public void trans(String userid) {
        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                    try {
                        Cat_arrayList.clear();
                        JSONObject main_obj = new JSONObject(result);
                    //    JSONObject priceass = main_obj.getJSONObject("msg");
                      //  linkk =  priceass.getString("next_page_url");
                        JSONArray prices = main_obj.getJSONArray("data");
                        if(prices.length() >0){
                        for (int index = 0; index < prices.length(); index++) {

                            JSONObject catgrey = prices.getJSONObject(index);

                            String title = catgrey.getString("name");
                            String photo = catgrey.getString("image_one");
                            String user_id = catgrey.getString("user_id");
                            String created = "hossam";
                            String id = catgrey.getString("id");
                            String content = catgrey.getString("content");
                            try {
                                    JSONObject city_obj = catgrey.getJSONObject("city");
                                    City_array = new ArrayList<Cities_items>();
                                    subnames = city_obj.getString("name");
                                    subids = city_obj.getString("id");
                                    City_array.add(new Cities_items(subids, subnames));

                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            try {
                            if (catgrey.has("user")) {

                                JSONObject catsub = catgrey.getJSONObject("user");
                                user_array = new ArrayList<User_items>();


                                String subname = catsub.getString("username");
                                String subid = catsub.getString("id");

                                String suborder = catsub.getString("mobile");


                                    user_array.add(new User_items(subid, subname, suborder));

                            }
                            }  catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Container_items items = new Container_items(id, title, photo, content, user_id, subnames, created, user_array);
                            Cat_arrayList.add(items);

                        }
                        }else {
                            Toast.makeText(MyADS.this,  getString(R.string.noads), Toast.LENGTH_SHORT).show();

                        }
                        removeSimpleProgressDialog();
                        adapter = new myads_adapter(MyADS.this,Cat_arrayList) ;
                        myads.setAdapter(adapter);
                        if (1 != 0) {
                        } else {
                            Toast.makeText(MyADS.this, "no Currency found!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        removeSimpleProgressDialog();

                        e.printStackTrace();
                        Toast.makeText(MyADS.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    removeSimpleProgressDialog();

                    Toast.makeText(MyADS.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();

                Toast.makeText(MyADS.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, MyADS.this, "http://alsog.net/api/advertises/myads/"+ userid, false);


    }
    public void transNext(String userid) {
        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                     try {

                        JSONObject main_obj = new JSONObject(result);
                        JSONObject priceass = main_obj.getJSONObject("msg");
                       linkk =  priceass.getString("next_page_url");

                        JSONArray prices = priceass.getJSONArray("data");


                        for (int index = 0; index < prices.length(); index++) {

                            JSONObject catgrey = prices.getJSONObject(index);
                            String id = catgrey.getString("id");
                            String title = catgrey.getString("title");
                            String content = catgrey.getString("content");
                            String image = catgrey.getString("image_one");
                            String user_id = catgrey.getString("user_id");
                            String created_id = catgrey.getString("created_at");
                            Log.i("images", image);
                            String mobile = catgrey.getString("mobile");
                            try {


                                JSONObject city_obj = catgrey.getJSONObject("citys");

                                City_array = new ArrayList<Cities_items>();


                                String subnames = city_obj.getString("name");
                                subids = city_obj.getString("id");
                                City_array.add(new Cities_items(subids, subnames));

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (catgrey.has("user")) {

                                    JSONObject catsub = catgrey.getJSONObject("user");
                                    user_array = new ArrayList<User_items>();


                                    String ids = catsub.getString("id");
                                    String name = catsub.getString("name");
                                    String mobiel = catsub.getString("mobile");


                                    user_array.add(new User_items(ids, name, mobiel));

                                }
                            }  catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Cat_arrayList.add(new Container_items(id, title,image,content,user_id,subnames,created_id,user_array));

                        }

                       loading = true;
                         adapter.notifyDataSetChanged();
                        if (1 != 0) {
                        } else {
                            Toast.makeText(MyADS.this, "no Currency found!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MyADS.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MyADS.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MyADS.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(MyADS.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, MyADS.this, userid, false);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backbtn:
                finish();
                break;
            case R.id.delete_fav:
                for(Container_items a: Cat_arrayList){
                    if(a.isSelected())
                        selectedAds.add(a);
                }
                for(int i = 0;i<selectedAds.size();i++){
                    String ll = selectedAds.get(i).getId();
                    ids.add(ll);
                }
                Toast.makeText(this,ids.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.linevaluate:
                Intent i = new Intent(MyADS.this,ActivityEvaluate.class);
                i.putExtra("userid",userid);
                if(adid!= null) {
                    i.putExtra("adid", adid);
                }
                startActivity(i);
                break;
            default:
                break;
        }
    }

    private void sendEvaluate(String message) {

        HashMap<String, String> map = new HashMap<String, String>();
         map.put(Const.URL,
                "http://bazar.net.sa/api/v1/rating/send?");
        map.put("seller_id",userid);
        map.put("api_token",helper.getAPI_TOKEN());
        map.put("text",message);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                Const.ServiceCode.SEND_EVALUATE, this, this));
    }

    private void openEvaluateDialog() {
        final Dialog dialog = new Dialog(this,R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.evaluate);


        final RadioButton yes_btn = (RadioButton) dialog.findViewById(R.id.yes_btn);
        final RadioButton no_btn = (RadioButton) dialog.findViewById(R.id.no_btn);
        final EditText evaluate_text = (EditText)dialog.findViewById(R.id.evaluate_text);
        ImageButton send = (ImageButton)dialog.findViewById(R.id.sendreportz) ;

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (no_btn.isChecked()) {
                    dialog.dismiss();
                    sendEvaluate(evaluate_text.getText().toString());

                } else if (yes_btn.isChecked()) {
                    dialog.dismiss();
                    sendEvaluate( evaluate_text.getText().toString());

                }
            }

        });
        dialog.show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        removeSimpleProgressDialog();
        Toast.makeText(MyADS.this,getString(R.string.error), Toast.LENGTH_LONG).show();
    }

    public void sendNotification(String message) throws JSONException {

        JSONArray tags = new JSONArray();
        JSONObject filters = new JSONObject();
        filters.put("field", "tag");
        filters.put("key", "id");
        filters.put("relation", "=");
        filters.put("value", userid);
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
                   // Toast.makeText(MyADS.this, result, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(MyADS.this, error, Toast.LENGTH_LONG).show();
                    removeSimpleProgressDialog();
                }
            }, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
            switch (serviceCode){
                case Const.ServiceCode.SEND_EVALUATE:
                    removeSimpleProgressDialog();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String stat= jsonObject.getString("status");
                        String msg = jsonObject.getString("msg");
                        Toast.makeText(this,msg, Toast.LENGTH_LONG ).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case Const.ServiceCode.SEND_MESSAGE:
                        removeSimpleProgressDialog();
                        //   try {
                        //   JSONObject jsonObject = new JSONObject(response);
                        //  if (jsonObject.getBoolean("success")) {
                        Toast.makeText(this, getString(R.string.messagesent), Toast.LENGTH_SHORT).show();
                        try {
                            if(!helper.getUserID().equals(userid)) {
                                sendNotification(getString(R.string.newmessage));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    break;
                default:
                    break;
            }
    }
}
