package com.bazar.bazars;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.bazar.bazars.Adapters.Comment_Adapter;
import com.bazar.bazars.Adapters.Details_Photo_Adapter;
import com.bazar.bazars.Adapters.Related_Details_Photo_Adapter;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.ParseContent;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.AdDetails;
import com.bazar.bazars.Items.AdPhotoItem;
import com.bazar.bazars.Items.Comment_Item;
import com.bazar.bazars.Items.Details_Photo_item;
import com.bazar.bazars.Items.Related_Details_Photo_item;

/**
 * Created by tournedo2003 on 3/14/17.
 */


public class DetailsActivity extends AppCompatActivity implements Response.ErrorListener, View.OnClickListener, AsyncTaskCompleteListener {

    TextView Label_adv, time_adv, user_poster, adv_num, where_post, details_of_subject;
    Button message_to_poster, write_comment;
    TextView phone_poster;
    private RecyclerView recyclerView_adv;
    private GridLayoutManager gridLayoutManager_Adv;
    private Details_Photo_Adapter adapter_adv;
    private List<Details_Photo_item> data_list_adv;
    ViewPager viewPager;
    ArrayList<AdDetails> allAds;
    ArrayList<String> photos;
    ImageButton sharebtn,backbtn,nextAd,previousAd;
    private RequestQueue requestQueue;
    PreferenceHelper helper;
    ParseContent parseContent;
    AdDetails details;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private Related_Details_Photo_Adapter adapter2;
    private List<Related_Details_Photo_item> data_list;
    boolean isPressed = false;
    Button send_comment;
    EditText the_comment;
    final int subscribe_check = 1;

    TextView count_likes;
    private ListView comments_list;
    private ArrayList<Comment_Item> Comment_Item_arrayList;
    private Comment_Adapter comment_adapter;
    String id;
    Button like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        Intent intent = getIntent();
         id = intent.getStringExtra("id_of_row");
        check_liked();
        check_subscripe();
        count_of_likes();
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        backbtn = (ImageButton)findViewById(R.id.backbtn);
        sharebtn = (ImageButton)findViewById(R.id.sharebtn);
        backbtn.setOnClickListener(this);
        sharebtn.setOnClickListener(this);
         //pagerAdapter = new CustomAdapter();
        requestQueue = Volley.newRequestQueue(this);
        helper = new PreferenceHelper(this);
        photos = new ArrayList<String>();
        parseContent = new ParseContent(this);
        details = new AdDetails();
        allAds = new ArrayList<AdDetails>();
        LayoutInflater inflater = getLayoutInflater();
        FrameLayout v0 = (FrameLayout) inflater.inflate (R.layout.addetailfragment, null);

        getAdDetail(id);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void check_subscripe() {
        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (result == Integer.toString(subscribe_check)) {
                    show_follow();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(DetailsActivity.this, "برجاء اعادة التحميل", Toast.LENGTH_SHORT).show();
            }
        }, DetailsActivity.this, "http://bazar.net.sa/api/v1/follow_comment/check/"+id+"?api_token=" +  helper.getAPI_TOKEN(), false);
    }

    private void getAdDetail(String id){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL,
                "http://bazar.net.sa/api/v1/post/" + id);
        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                Const.ServiceCode.GET_Ad_Details, this, this));
    }

    public void show_follow() {

        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    if (result != null) {

                        JSONObject main_obj = new JSONObject(result);
                        JSONArray equ_obj = main_obj.getJSONArray("data");
                        for (int a = 0; a < equ_obj.length(); a++) {
                            JSONObject object = equ_obj.getJSONObject(a);
                            String user_id_json = object.getString("user_id");
                            String post_id_json = object.getString("post_id");

                           // Toast.makeText(DetailsActivity.this, "+user_id_json+ post_id_json" + user_id_json + post_id_json, Toast.LENGTH_SHORT).show();


                        }
                       // adapter2.notifyDataSetChanged();


                    } else {
                        Toast.makeText(DetailsActivity.this, "برجاء اعادة التحميل", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(DetailsActivity.this, "برجاء اعادة التحميل", Toast.LENGTH_SHORT).show();

            }
        }, DetailsActivity.this, "http://bazar.net.sa/api/v1/follow_comment/15554?api_token=" +  helper.getAPI_TOKEN(), false);

    }


    public void check_liked() {

        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    if (result != null) {

                        JSONObject main_obj = new JSONObject(result);
                        String like_status = main_obj.getString("status");

                        if (like_status == "1") {
                          //  like.setBackgroundResource(R.drawable.unlikee);
                        } else {
                           // like.setBackgroundResource(R.drawable.likee);
                        }


                    } else {
                        Toast.makeText(DetailsActivity.this, "برجاء اعادة التحميل", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(DetailsActivity.this, "برجاء اعادة التحميل", Toast.LENGTH_SHORT).show();

            }
        }, DetailsActivity.this, "http://bazar.net.sa/api/v1/favorite/110/" + id, false);


    }


    public void count_of_likes() {

        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    if (result != null) {

                        final String numbers = result.toString();
                        //count_likes.setText(numbers);


                    } else {
                        Toast.makeText(DetailsActivity.this, "برجاء اعادة التحميل", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(DetailsActivity.this, "برجاء اعادة التحميل", Toast.LENGTH_SHORT).show();

            }
        }, DetailsActivity.this, "http://bazar.net.sa/api/v1/favorite/" + id, false);


    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }
    private void showAlertDialog() {
        PackageManager pm = getPackageManager();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        GridView gridView = new GridView(this);
        final Intent email = new Intent(Intent.ACTION_SEND);
        final AppAdapter adapter ;
        email.setType("text/plain");
        email.putExtra(Intent.EXTRA_TEXT, "اعجبني هذا الاعلان " +" " + "http://bazar.net.sa/api/v1/post/" + id);
        List<ResolveInfo> launchables = pm.queryIntentActivities(email, 0);

        Collections
                .sort(launchables, new ResolveInfo.DisplayNameComparator(pm));

        adapter = new AppAdapter(this,pm, launchables);
        gridView.setNumColumns(1);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generated method stub
                ResolveInfo launchable = adapter.getItem(position);
                ActivityInfo activity = null;
                if (launchable != null) {
                    activity = launchable.activityInfo;
                }
                assert activity != null;
                ComponentName name = new ComponentName(
                        activity.applicationInfo.packageName, activity.name);
                email.setComponent(name);
                startActivity(email);
            }
        });


        builder.setView(gridView);

        builder.show();
    }
    private class AppAdapter extends ArrayAdapter<ResolveInfo> {
        private PackageManager pm = null;

        AppAdapter(Context context, PackageManager pm, List<ResolveInfo> apps) {
            super(context, R.layout.chooserrow, apps);
            this.pm = pm;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = newView(parent);
            }

            bindView(position, convertView);

            return (convertView);
        }

        private View newView(ViewGroup parent) {
            return ( getLayoutInflater().inflate(R.layout.chooserrow, parent, false));
        }

        private void bindView(int position, View row) {
            TextView label = (TextView) row.findViewById(R.id.label);

            label.setText(getItem(position).loadLabel(pm));

            ImageView icon = (ImageView) row.findViewById(R.id.icon);

            icon.setImageDrawable(getItem(position).loadIcon(pm));
        }
    }


    private class CustomAdapter extends FragmentPagerAdapter {
        AdDetails adDetails;
        ArrayList<String> photoss;
        CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext, AdDetails detaila, ArrayList<String> photos) {
            super(supportFragmentManager);
            this.adDetails = detaila;
            this.photoss = photos;
            //allAds = detailses;
        }
        public ArrayList<Fragment> fragments;

        @Override
        public Fragment getItem(int position) {
            //position = helper.getfragPosition();
            //fragments = new ArrayList<Fragment>();

            return null;
        }



        @Override
        public int getCount() {
            return 1;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbtn:
                finish();
                break;
            case R.id.sharebtn:
                showAlertDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {
            case Const.ServiceCode.GET_Ad_Details:

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject object = jsonObject.getJSONObject("post");
                     AdPhotoItem photoItem = new AdPhotoItem();

                    details.setAdId(object.getInt("id"));
                    details.setUserId(object.getString("user_id"));
                    details.setCreatedDate(object.getString("created_at"));
                    details.setContent(object.getString("content"));
                    details.setTitle(object.getString("title"));
                    details.setUsername(object.getJSONObject("user").getString("name"));
                    details.setMobile(object.getJSONObject("user").getString("mobile"));
                    details.setCity(object.getJSONObject("citys").getString("name"));
                    //adDetails.setImageone(object.getString("image_one"));

                    JSONArray array_inages = jsonObject.getJSONArray("images");

                    for (int x = 0; x < array_inages.length(); x++) {
                        String photoItema=array_inages.getString(x);
                        photos.add(photoItema);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(),getApplicationContext(),details,photos));


//Add a new Fragment to the list with bundle
                //Bundle b = new Bundle();
                // b.putInt("position", i);
                //  String title = "asd";
                //pagerAdapter.add(AdDetailsFragment.class, title, b);
                //pagerAdapter.notifyDataSetChanged();
                //viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(),allAds));
                // viewPager.setCurrentItem(helper.getfragPosition());
                break;
        }
    }
}
