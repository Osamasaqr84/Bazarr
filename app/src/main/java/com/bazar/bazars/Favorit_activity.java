package com.bazar.bazars;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bazar.bazars.Adapters.myads_adapter;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.Cities_items;
import com.bazar.bazars.Items.Container_items;
import com.bazar.bazars.Items.User_items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tournedo2003 on 3/28/17.
 */

public class Favorit_activity extends AppCompatActivity implements View.OnClickListener {
    String subids;
    public RecyclerView myads;
    public static myads_adapter adapter ;
    public static ArrayList<Container_items> Cat_arrayList;
    public static ArrayList<Cities_items> City_array;
    public static ArrayList<User_items> user_array;
    ImageButton backbtn,delete_favbtn;
    RelativeLayout toolbar;
    PreferenceHelper helper;
    String subnames = "";
    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite);
        toolbar = (RelativeLayout)findViewById(R.id.radioGroupParent);
        myads = (RecyclerView) findViewById(R.id.ads_listView);
        Intent intent = getIntent();
        helper = new PreferenceHelper(this);
        backbtn = (ImageButton)findViewById(R.id.backbtn);
        delete_favbtn = (ImageButton)findViewById(R.id.delete_fav);
        backbtn.setOnClickListener(this);
        delete_favbtn.setOnClickListener(this);
        String link = intent.getStringExtra("link");
        trans(link);
        LinearLayoutManager manager = new LinearLayoutManager(Favorit_activity.this);
        myads.setHasFixedSize(true);
        myads.setLayoutManager(manager);
    }



    public void trans(String apitoken) {
        showSimpleProgressDialog(this,"",getString(R.string.pleasewait),false);
        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                    Cat_arrayList = new ArrayList<Container_items>();
                    try {

                        JSONObject main_obj = new JSONObject(result);

                        JSONArray prices = main_obj.getJSONArray("data");
                        String nexturl =  "" ;
                        String prevurl = ""  ;

                        for (int index = 0; index < prices.length(); index++) {

                            JSONObject catgrey = prices.getJSONObject(index);
                                try {
                                    JSONObject city_obj = catgrey.getJSONObject("advertise");
                                    String id = city_obj.getString("id");
                                    subnames = city_obj.getJSONObject("city").getString("name");

                                    String title = city_obj.getString("name");
                                    String content = city_obj.getString("content");
                                    JSONArray array = city_obj.getJSONArray("photos");
                                    String image = "";
                                    if(array.length()>0) {
                                         image = array.getJSONObject(0).getString("photo");
                                    }
                                    user_array = new ArrayList<User_items>();
                                    JSONObject users = city_obj.getJSONObject("user");
                                    User_items item = new User_items(users.getString("id"),users.getString("username"),users.getString("mobile"));
                                    user_array.add(item);
                                    String user_id = city_obj.getString("user_id");
                                    String created_id = city_obj.getString("modified").replace("T"," ");
                                    subids = id;
                                    Log.i("images", image);
                                    Cat_arrayList.add(new Container_items(id, title, image, content, user_id, subnames,  created_id, user_array));

                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }

                        removeSimpleProgressDialog();
                        adapter = new myads_adapter(Favorit_activity.this ,  Cat_arrayList) ;
                        myads.setAdapter(adapter);

                    } catch (JSONException e) {
                        removeSimpleProgressDialog();
                        e.printStackTrace();
                    }
                } else {
                    removeSimpleProgressDialog();
                    Toast.makeText(Favorit_activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
                Toast.makeText(Favorit_activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, Favorit_activity.this, "http://alsog.net/api/Likes/adsfavorite/"+helper.getUserID(), false);
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

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete_fav:

                break;
            case R.id.backbtn:
                finish();
                break;
            default:
                break;

        }
    }

    private void deletFavourite(final String id) {

        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                    try{
                        int success = Integer.parseInt(result);
                        if(success == 0){
                            Toast.makeText(Favorit_activity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    } finally {

                    }
                } else {
                    Toast.makeText(Favorit_activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                // Toast.makeText(MyADS.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(Favorit_activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, Favorit_activity.this, "http://bazar.net.sa/api/v1/favorite/delete/"+id+"?api_token=" +  helper.getAPI_TOKEN()  , false);
    }
}
