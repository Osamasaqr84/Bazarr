package com.bazar.bazars;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
 * Created by tournedo2003 on 3/29/17.
 */
public class Search_results extends AppCompatActivity {
    public ArrayList<Container_items> Cat_arrayList;
    public ArrayList<Cities_items> City_array;
    public ArrayList<User_items> user_array;
    String subnames;
    public  myads_adapter adapter ;
     public RecyclerView myads;
    ImageButton backbtn;
    String linkk = null;
     ImageButton count;
    private boolean loading = true;
    LinearLayoutManager manager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    PreferenceHelper helper;
   String cattype,idz,text;
    int type,page = 2;
    ArrayList<String> kk = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        backbtn = (ImageButton)findViewById(R.id.backbtn);
        Cat_arrayList = new ArrayList<Container_items>();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        helper = new PreferenceHelper(this);
        myads = (RecyclerView) findViewById(R.id.ads_listView);
         myads.setHasFixedSize(true);
        manager = new LinearLayoutManager(Search_results.this);
        myads.setLayoutManager(manager);
        if(intent.getStringExtra("cattype") != null) {
            cattype = intent.getStringExtra("cattype");
        }
        if(intent.getIntExtra("idz",0) != 0) {
            idz = String.valueOf(intent.getIntExtra("idz",0));
        }
        if(intent.getStringExtra("text") != null) {
            text = intent.getStringExtra("text");
        }
        type = intent.getIntExtra("type",0);
        trans(cattype,type);
        count = (ImageButton)findViewById(R.id.delete_fav);
        count.setVisibility(View.INVISIBLE);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Search_results.this, String.valueOf(kk.size()), Toast.LENGTH_SHORT).show();
            }
        });

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
                          //  if (linkk.length() > 4) {
                            if(type ==0){
                                transNext("http://alsog.net/api/advertises/alldata/"
                                        +  cattype + "/" + idz + "/" + 0 + "/" + helper.getCityId() + "/" + page,0);
                            } else {
                                transNext("http://alsog.net/api/advertises/searchbyword/" + page,1);
                            }
                        }
                    }
                }
            }
        });

    }

    ArrayList<User_items> users_arrayList ;
    public void trans(String link, int type) {
         if(type==0) {
            new Json_Controller().GetDataFromServer(new VolleyCallback() {
                @Override
                public void onSuccess(String result) {

                    if (!result.equals("")) {
                        Cat_arrayList.clear();
                        try {

                            JSONObject main_obj = new JSONObject(result);
                            JSONArray ads = main_obj.getJSONArray("data");
                            // String nexturl =  main_obj.getString("next_page_url") ;
                            //String prevurl = main_obj.getString("prev_page_url")   ;
                            //    helper.setNEXT_URL(nexturl);
                            //       linkk = nexturl;
                            for (int index = 0; index < ads.length(); index++) {

                                JSONObject catgrey = ads.getJSONObject(index);
                                JSONObject cityjson = catgrey.getJSONObject("city");
                                Log.i("name2", String.valueOf(catgrey));
                                String title = catgrey.getString("name");
                                String photo = catgrey.getString("image_one");
                                String city = cityjson.getString("name");
                                String user_id = catgrey.getString("user_id");
                                String created = catgrey.getString("created");
                                String id = catgrey.getString("id");
                                String content = catgrey.getString("content");

                                users_arrayList = new ArrayList<User_items>();

                                if (catgrey.has("user")) {
                                    try {
                                        JSONObject catsub = catgrey.getJSONObject("user");


                                        String subname = catsub.getString("username");
                                        String subid = catsub.getString("id");

                                        String suborder = catsub.getString("mobile");
                                        users_arrayList.add(new User_items(subid, subname, suborder));
                                    } catch (Exception e) {
                                    }
                                }
                                Cat_arrayList.add(new Container_items(id, title, photo, content, user_id, city, created, users_arrayList));

                            }

                            adapter = new myads_adapter(Search_results.this, Cat_arrayList);
                            myads.setAdapter(adapter);

                            // refreshLayout.setRefreshing(false);
                            transNext("http://alsog.net/api/advertises/alldata/"
                                    +  cattype + "/" + idz + "/" + 0 + "/" + helper.getCityId() + "/" + page,0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Search_results.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Search_results.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(Search_results.this,getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
                }
            }, Search_results.this, "http://alsog.net/api/advertises/alldata/"
                                    +  cattype + "/" + idz + "/" + 0 + "/" + helper.getCityId() + "/1", false);

        }else if(type==1){
             JSONObject oj = new JSONObject();
             try{
                 oj.put("name",text);
             } catch (JSONException e) {
                 e.printStackTrace();
             }
            final String requestbody = oj.toString();
             new Json_Controller().PostData2Server(Search_results.this, "http://alsog.net/api/advertises/searchbyword/1",
                     requestbody, new VolleyCallback() {
                         @Override
                         public void onSuccess(String result) throws JSONException {
                             if (!result.equals("")) {
                                 Cat_arrayList.clear();
                                 try {

                                     JSONObject main_obj = new JSONObject(result);
                                     JSONArray ads = main_obj.getJSONArray("data");
                                     // String nexturl =  main_obj.getString("next_page_url") ;
                                     //String prevurl = main_obj.getString("prev_page_url")   ;
                                     //    helper.setNEXT_URL(nexturl);
                                     //       linkk = nexturl;
                                     for (int index = 0; index < ads.length(); index++) {

                                         JSONObject catgrey = ads.getJSONObject(index);
                                         JSONObject cityjson = catgrey.getJSONObject("city");
                                         Log.i("name2", String.valueOf(catgrey));
                                         String title = catgrey.getString("name");

                                         JSONArray pho = catgrey.getJSONArray("photos");
                                         String photo ="";
                                         if(pho.length()>0){
                                             photo = pho.getJSONObject(0).getString("photo");
                                         }

                                         String city = cityjson.getString("name");
                                         String user_id = catgrey.getString("user_id");
                                         String created = catgrey.getString("created");
                                         String id = catgrey.getString("id");
                                         String content = catgrey.getString("content");

                                         users_arrayList = new ArrayList<User_items>();

                                         if (catgrey.has("user")) {
                                             try {
                                                 JSONObject catsub = catgrey.getJSONObject("user");


                                                 String subname = catsub.getString("username");
                                                 String subid = catsub.getString("id");

                                                 String suborder = catsub.getString("mobile");
                                                 users_arrayList.add(new User_items(subid, subname, suborder));
                                             } catch (Exception e) {
                                             }
                                         }
                                         Cat_arrayList.add(new Container_items(id, title, photo, content, user_id, city, created, users_arrayList));

                                     }

                                     adapter = new myads_adapter(Search_results.this, Cat_arrayList);
                                     myads.setAdapter(adapter);

                                     // refreshLayout.setRefreshing(false);
                                     transNext("http://alsog.net/api/advertises/searchbyword/" + page,1);
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                  }
                             } else {
                                 Toast.makeText(Search_results.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onError(String error) {
                             Toast.makeText(Search_results.this,getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
                         }
                     },false);
        }
    }



    public void transNext(String link, int type) {
        if(type==0) {
            new Json_Controller().GetDataFromServer(new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    if (!result.equals("")) {
                         try {
                            page = page +1;
                            JSONObject main_obj = new JSONObject(result);
                            JSONArray ads = main_obj.getJSONArray("data");

                            for (int index = 0; index < ads.length(); index++) {

                                JSONObject catgrey = ads.getJSONObject(index);
                                JSONObject cityjson = catgrey.getJSONObject("city");
                                Log.i("name2", String.valueOf(catgrey));
                                String title = catgrey.getString("name");
                                String photo = catgrey.getString("image_one");
                                String city = cityjson.getString("name");
                                String user_id = catgrey.getString("user_id");
                                String created = catgrey.getString("created");
                                String id = catgrey.getString("id");
                                String content = catgrey.getString("content");

                                users_arrayList = new ArrayList<User_items>();

                                if (catgrey.has("user")) {
                                    try {
                                        JSONObject catsub = catgrey.getJSONObject("user");


                                        String subname = catsub.getString("username");
                                        String subid = catsub.getString("id");

                                        String suborder = catsub.getString("mobile");
                                        users_arrayList.add(new User_items(subid, subname, suborder));
                                    } catch (Exception e) {
                                    }
                                }
                                Cat_arrayList.add(new Container_items(id, title, photo, content, user_id, city, created, users_arrayList));

                            }

                            adapter = new myads_adapter(Search_results.this, Cat_arrayList);
                            myads.setAdapter(adapter);

                            // refreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Search_results.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Search_results.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String error) {
                  //  Toast.makeText(Search_results.this,getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
                }
            }, Search_results.this, link, false);

        }else if(type==1){
            JSONObject oj = new JSONObject();
            try{
                oj.put("name",text);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String requestbody = oj.toString();
            new Json_Controller().PostData2Server(Search_results.this, "http://alsog.net/api/advertises/searchbyword/" + page,
                    requestbody, new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) throws JSONException {
                            if (!result.equals("")) {
                                Cat_arrayList.clear();
                                try {

                                    JSONObject main_obj = new JSONObject(result);
                                    JSONArray ads = main_obj.getJSONArray("data");
                                    // String nexturl =  main_obj.getString("next_page_url") ;
                                    //String prevurl = main_obj.getString("prev_page_url")   ;
                                    //    helper.setNEXT_URL(nexturl);
                                    //       linkk = nexturl;
                                    for (int index = 0; index < ads.length(); index++) {

                                        JSONObject catgrey = ads.getJSONObject(index);
                                        JSONObject cityjson = catgrey.getJSONObject("city");
                                        Log.i("name2", String.valueOf(catgrey));
                                        String title = catgrey.getString("name");
                                        String photo = catgrey.getString("image_one");
                                        String city = cityjson.getString("name");
                                        String user_id = catgrey.getString("user_id");
                                        String created = catgrey.getString("created");
                                        String id = catgrey.getString("id");
                                        String content = catgrey.getString("content");

                                        users_arrayList = new ArrayList<User_items>();

                                        if (catgrey.has("user")) {
                                            try {
                                                JSONObject catsub = catgrey.getJSONObject("user");


                                                String subname = catsub.getString("username");
                                                String subid = catsub.getString("id");

                                                String suborder = catsub.getString("mobile");
                                                users_arrayList.add(new User_items(subid, subname, suborder));
                                            } catch (Exception e) {
                                            }
                                        }
                                        Cat_arrayList.add(new Container_items(id, title, photo, content, user_id, city, created, users_arrayList));

                                    }

                                    adapter = new myads_adapter(Search_results.this, Cat_arrayList);
                                    myads.setAdapter(adapter);

                                    // refreshLayout.setRefreshing(false);
                               } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(Search_results.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Search_results.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(String error) {
                          //  Toast.makeText(Search_results.this,getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
                        }
            }, false);
        }
    }

}
