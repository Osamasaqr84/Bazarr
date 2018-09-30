package com.bazar.bazars;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bazar.bazars.Adapters.Comment_Adapter;
import com.bazar.bazars.Adapters.Details_Photo_Adapter;
import com.bazar.bazars.Adapters.Related_Adapter;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.ParseContent;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Helper.ApiClient;
import com.bazar.bazars.Helper.ApiInterface;
import com.bazar.bazars.Items.AdDetails;
import com.bazar.bazars.Items.AdPhotoItem;
import com.bazar.bazars.Items.Comment_Item;
import com.bazar.bazars.Items.Comment_model;
import com.bazar.bazars.Items.Related_Item;
import com.bazar.bazars.Models.Delete_Ad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ADetailed extends AppCompatActivity implements Response.ErrorListener, AsyncTaskCompleteListener, View.OnClickListener {

    RecyclerView AddPhotosRecy, commnetsRecycler, relatedRecycler;
    ArrayList<String> adPhotoItemPhotos;
    ImageView followIcon;
    ImageButton AdFavBtn, reportbtn;
    LinearLayout followLinear, messageLinear, callLinear, evaluate;
    TextView adTitle, adUserName, adNumber, adTime, adCity, adContent, adMobile, countlike;
    int favStatues, commentStatues, messageStatues;
    ArrayList<Comment_model> comments;
    LinearLayout commentlin;
    Details_Photo_Adapter photo_adapter;
    ArrayList<AdDetails> allAds;
    ArrayList<String> photos;
    ArrayList<Related_Item> relatedItems;
    ImageButton sharebtn, backbtn, menubtn;
    private RequestQueue requestQueue;
    PreferenceHelper helper;
    ParseContent parseContent;
    AdDetails details;
    private GridLayoutManager relatedManager;
    LinearLayoutManager gridLayoutManager;
    String id, isFollow, userid;
    Comment_model comment_model;
    ArrayList<Comment_Item> commentitems;
    PopupMenu popup;
    Receiver receiver;
    boolean isRecieverRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adetails);
        parseContent = new ParseContent(this);
        helper = new PreferenceHelper(this);
        Intent intent = getIntent();
        commentitems = new ArrayList<Comment_Item>();
        isFollow = "";
        countlike = (TextView) findViewById(R.id.countlike);
        evaluate = (LinearLayout) findViewById(R.id.adEvaluation);
        evaluate.setOnClickListener(this);
        commentlin = (LinearLayout) findViewById(R.id.commentLinear);
        reportbtn = (ImageButton) findViewById(R.id.adViolencebtn);
        reportbtn.setOnClickListener(this);
        commentlin.setOnClickListener(this);
        menubtn = (ImageButton) findViewById(R.id.settingmenu);
        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = new PopupMenu(ADetailed.this, menubtn, Gravity.RIGHT);

                popup.getMenuInflater()
                        .inflate(R.menu.menu_main, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editad:
                                Intent i = new Intent(ADetailed.this, Edit_Ad.class);
                                i.putExtra("adid", id);
                                i.putExtra("city", details.getCityid());
                                String dd = details.getCityid();
                                i.putExtra("cat1", details.getCat1());
                                i.putExtra("cat2", details.getCat2());
                                i.putExtra("cat3", details.getCat3());
                                startActivity(i);
                                finish();
                                break;
                            case R.id.refreshad:
                                refreshAd();
                                break;
                            case R.id.deleted:
                                deleteAd(id);
                                break;

                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        relatedManager = new GridLayoutManager(this, 3);
        relatedRecycler = (RecyclerView) findViewById(R.id.relatedRecycler);
        relatedRecycler.setLayoutManager(relatedManager);
        relatedRecycler.setHorizontalScrollBarEnabled(true);
        relatedItems = new ArrayList<Related_Item>();
        relatedRecycler.setItemAnimator(new DefaultItemAnimator());
        id = intent.getStringExtra("id_of_row");
        userid = intent.getStringExtra("user_id");
        backbtn = (ImageButton) findViewById(R.id.backbtn);
        sharebtn = (ImageButton) findViewById(R.id.sharebtn);
        backbtn.setOnClickListener(this);
        sharebtn.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
        photos = new ArrayList<String>();
        parseContent = new ParseContent(this);
        details = new AdDetails();
        allAds = new ArrayList<AdDetails>();
        adPhotoItemPhotos = new ArrayList<String>();
        adTitle = (TextView) findViewById(R.id.adtitle);
        adNumber = (TextView) findViewById(R.id.adnumber);
        adUserName = (TextView) findViewById(R.id.adUserName);
        adUserName.setOnClickListener(this);
        adTime = (TextView) findViewById(R.id.adtime);
        commnetsRecycler = (RecyclerView) findViewById(R.id.commentRecycler);
        commnetsRecycler.setLayoutManager(new LinearLayoutManager(this));
        commnetsRecycler.setItemAnimator(new DefaultItemAnimator());
        commnetsRecycler.setNestedScrollingEnabled(false);
        followLinear = (LinearLayout) findViewById(R.id.followLinear);
        followLinear.setOnClickListener(this);
        comments = new ArrayList<Comment_model>();
        messageLinear = (LinearLayout) findViewById(R.id.messageLinear);
        messageLinear.setOnClickListener(this);
        callLinear = (LinearLayout) findViewById(R.id.callLinear);
        callLinear.setOnClickListener(this);
        followIcon = (ImageView) findViewById(R.id.followicon);
        adContent = (TextView) findViewById(R.id.adContent);
        adMobile = (TextView) findViewById(R.id.adUserMobile);
        AdFavBtn = (ImageButton) findViewById(R.id.adFavbtn);
        AdFavBtn.setOnClickListener(this);
        adCity = (TextView) findViewById(R.id.adCity);
        AddPhotosRecy = (RecyclerView) findViewById(R.id.AddImages);
        //AddPhotosRecy.setItemAnimator(new DefaultItemAnimator());
        AddPhotosRecy.setHasFixedSize(true);
        //AddPhotosRecy.setNestedScrollingEnabled(true);
        // AddPhotosRecy.setAdapter(photosAdapter);
        followLinear = (LinearLayout) findViewById(R.id.followLinear);
        followLinear.setOnClickListener(this);
        getAdDetail(id);
    }
    public String getCurrentTime(String adDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentdate = "";

      //  long different = endDate.getTime() - startDate.getTime();
        try{
        if (adDate != null) {
            Date date = format.parse(adDate);
            Date cdate = new Date(System.currentTimeMillis());

            long different = cdate.getTime() - date.getTime();


            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;
            long monthesInMilli = daysInMilli * 30;
            long yearInMilli = monthesInMilli * 12;

            long elapsedyears = different / yearInMilli;
            different = different % yearInMilli;

            long elapsedmonths = different / monthesInMilli;
            different = different % monthesInMilli;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;


            if (elapsedyears >= 1) {
                currentdate = getString(R.string.before) + " " + elapsedyears + " " + getString(R.string.year) + " " + getString(R.string.and) + " " + elapsedmonths + " " + getString(R.string.month);
            } else if (elapsedmonths >= 1) {
                currentdate = getString(R.string.before) + " " + elapsedmonths + " " + getString(R.string.month) + " " + getString(R.string.and) + " " + elapsedDays + " " + getString(R.string.day);
            } else if (elapsedDays >= 1) {
                currentdate = getString(R.string.before) + " " + elapsedDays + " " + getString(R.string.day) + " " + getString(R.string.and) + " " + elapsedHours + " " + getString(R.string.hour);
            } else if (elapsedHours >= 1) {
                currentdate = getString(R.string.before) + " " + elapsedHours + " " + getString(R.string.hour) + " " + getString(R.string.and) + " " + elapsedMinutes + " " + getString(R.string.min);
            } else {
                currentdate = getString(R.string.before) + " " + elapsedMinutes + " " + getString(R.string.min);
            }
        }
        } catch (ParseException e) {
                // TODO Auto-generated catch blockf
                e.printStackTrace();
            }
        return currentdate;

    }
//    public String getCurrentTime(String adDate) {
//        String myStrDate = adDate;
//        String currentdate = "";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Double  days, hours, min,month,year;
//        DecimalFormat precision = new DecimalFormat("0");
//
//        try {
//            if (myStrDate != null) {
//                Date date = format.parse(myStrDate);
//                Date cdate = new Date(System.currentTimeMillis());
//
//                long difference = cdate.getTime() - date.getTime();
//                double la = Double.parseDouble(String.valueOf(difference));
////                int lka = Integer.parseInt(String.valueOf(difference));
//                year = (double) (la /(1000 * 60 * 60 * 24 * 30 * 12));
//                month = (double) (la -(1000 * 60 * 60 * 24 * 30 * 12 * year));
//                days = (double) (la - (1000 * 60 * 60 * 24 * 30 * month));
//                hours = (double) (la - (1000 * 60 * 60 * 24 * days) );
//                min = (double) (la - (1000 * 60 * 60 * hours));
//                int i =  days.intValue();
//
//                if(year >=1){
//                   currentdate = getString(R.string.before) + " " + precision.format(year)+ " " + getString(R.string.year) + " " + getString(R.string.and) + " " + precision.format(month) + " " + getString(R.string.month);
//               } else if(month>=1){
//                   currentdate = getString(R.string.before) + " " + precision.format(month)+ " " + getString(R.string.month) + " " + getString(R.string.and) + " " + precision.format(days) + " " + getString(R.string.day);
//               }else if (days >= 1) {
//                    currentdate = getString(R.string.before) + " " + String.valueOf(i) + " " + getString(R.string.day) + " " + getString(R.string.and) + " " + precision.format(hours) + " " + getString(R.string.hour);
//                } else if (hours >= 1) {
//                    currentdate = getString(R.string.before) + " " + precision.format(hours )+ " " + getString(R.string.hour) + " " + getString(R.string.and) + " " + precision.format(min )+ " " + getString(R.string.min);
//                } else {
//                    currentdate = getString(R.string.before) + " " + precision.format(min )+ " " + getString(R.string.min);
//                }
//            }
//            //Toast.makeText(activity,String.valueOf("days" +days+ "hours"+ hours +"min" + min),Toast.LENGTH_SHORT).show();
//
//        } catch (ParseException e) {
//            // TODO Auto-generated catch blockf
//            e.printStackTrace();
//        }
//
//        return currentdate;
//    }

    private void getAdDetail(String id) {
        showSimpleProgressDialog(this, "", getString(R.string.pleasewait), false);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        HashMap<String, String> map = new HashMap<String, String>();
        if(helper.getUserID() != null){
            map.put(Const.URL,
                    "http://alsog.net/api/advertises/adsdetails/" + id +"/" + helper.getUserID());
        }else {
            map.put(Const.URL,
                    "http://alsog.net/api/advertises/adsdetails/" + id +"/0");
        }

        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                Const.ServiceCode.GET_Ad_Details, this, this, headers));
    }

    public void count_of_likes() {

        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    if (result != null) {

                        final String numbers = result.toString();
                        countlike.setText(numbers);
                    } else {
                        Toast.makeText(ADetailed.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ADetailed.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                removeSimpleProgressDialog();

            }
        }, ADetailed.this, "http://bazar.net.sa/api/v1/favorite/" + id, false);


    }

    private void showAlertDialog() {
        PackageManager pm = getPackageManager();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        GridView gridView = new GridView(this);
        final Intent email = new Intent(Intent.ACTION_SEND);
        final AppAdapter adapter;
        email.setType("text/plain");
        email.putExtra(Intent.EXTRA_TEXT, "اعجبني هذا الاعلان " + " " + "http://bazar.net.sa/post/" + id);
        List<ResolveInfo> launchables = pm.queryIntentActivities(email, 0);

        Collections
                .sort(launchables, new ResolveInfo.DisplayNameComparator(pm));

        adapter = new AppAdapter(this, pm, launchables);
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
            return (getLayoutInflater().inflate(R.layout.chooserrow, parent, false));
        }

        private void bindView(int position, View row) {
            TextView label = (TextView) row.findViewById(R.id.label);

            label.setText(getItem(position).loadLabel(pm));

            ImageView icon = (ImageView) row.findViewById(R.id.icon);

            icon.setImageDrawable(getItem(position).loadIcon(pm));
        }
    }


    public void startGalleryActivity() {
        ArrayList<Integer> images = new ArrayList<>();

        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putIntegerArrayListExtra(GalleryActivity.EXTRA_NAME, images);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        removeSimpleProgressDialog();
        Toast.makeText(ADetailed.this, getString(R.string.error), Toast.LENGTH_SHORT).show();

    }

    private void checkIfAdInFavourite() {
        if (helper.getAPI_TOKEN() != null) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(Const.URL,
                    "http://bazar.net.sa/api/v1/favorite/" + helper.getUserID() + "/" + id);
            requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                    Const.ServiceCode.CHECK_IF_AD_FAVOURITE, this, this));
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

    private void putAdToMyFavourite() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL,
                "http://alsog.net/api/likes/add.json");
        map.put("user_id", helper.getUserID());
        map.put("advertise_id", id);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                Const.ServiceCode.PUT_AD_INFAVOURTIE, this, this, headers));
    }

    private void removeAdFromMyFavourite() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL,
                "http://bazar.net.sa/api/v1/favorite/delete/" + id + "?api_token=" + helper.getAPI_TOKEN());
        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                Const.ServiceCode.REMOVE_AD_FROMFAVOURTIE, this, this));
    }

    Comment_Adapter adapter;
    String seller_id = "";
    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {
            case Const.ServiceCode.GET_Ad_Details:
                removeSimpleProgressDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("chklikess") == 0){
                        AdFavBtn.setImageResource(R.mipmap.hearticon);
                    } else {
                        AdFavBtn.setImageResource(R.mipmap.hearticoncopy);
                    }
                    if(jsonObject.getInt("chkfollwerss") == 0){
                        commentStatues = 0;
                        followIcon.setImageResource(R.drawable.subscribe);
                    } else {
                        commentStatues = 1;
                        followIcon.setImageResource(R.drawable.subscribed);
                    }
                    JSONArray objecta = jsonObject.getJSONArray("data");
                    JSONObject object = objecta.getJSONObject(0);
                    JSONArray related = jsonObject.getJSONArray("related");
                    AdPhotoItem photoItem = new AdPhotoItem();
                    details.setAdId(object.getInt("id"));
                    details.setUserId(object.getString("user_id"));
                    seller_id = object.getString("user_id");
                    details.setCreatedDate(object.getString("date"));
                    details.setContent(object.getString("content"));
                    details.setTitle(object.getString("name"));
                    details.setUsername(object.getJSONObject("user").getString("username"));
                    details.setMobile(object.getString("mobile"));
                    details.setCreatedDate(object.getString("modified").replace("T"," "));
                    details.setCity(object.getJSONObject("city").getString("name"));
                    details.setCityid(String.valueOf(object.getInt("city_id")));
                    details.setCat1(object.getString("category_id"));
                    details.setCat2(object.getString("sub_category_id"));
                    details.setCat3(object.getString("sub_mini_category_id"));
                    JSONArray array_inages = object.getJSONArray("photos");

                    for (int x = 0; x < array_inages.length(); x++) {
                        JSONObject aa = array_inages.getJSONObject(x);
                        photos.add("http://alsog.net/library/photos/"
                                + aa.getString("photo"));
                    }

                    photo_adapter = new Details_Photo_Adapter(this, photos);
                    gridLayoutManager = new LinearLayoutManager(this);
                    AddPhotosRecy.setLayoutManager(gridLayoutManager);

                    if (photos.size() == 0) {
                        AddPhotosRecy.setVisibility(View.GONE);
                    } else {
                        AddPhotosRecy.setMinimumHeight(photos.size() * 200);
                        AddPhotosRecy.setAdapter(photo_adapter);
                    }

                    if(related.length()!=0) {
                        for (int x = 0; x < related.length(); x++) {
                            Related_Item related_item = new Related_Item();
                            JSONObject objects = related.getJSONObject(x);
                            if(objects.getJSONArray("photos").length() >0) {
                                related_item.setImage("http://alsog.net/library/photos/"
                                        + objects.getJSONArray("photos").getJSONObject(0).getString("photo"));
                                related_item.setPost_id(objects.getString("id"));
                            } else {
                                related_item.setImage("");
                            }
                            related_item.setUserid(objects.getString("user_id"));
                            relatedItems.add(related_item);

                        }
                        if (relatedItems.size() > 0) {
                            Related_Adapter relatedAdapter = new Related_Adapter(this, relatedItems);
                            relatedRecycler.setAdapter(relatedAdapter);
                        }
                    }
                        JSONArray jsonArray = object.getJSONArray("comments");
                        commentitems.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Comment_Item commentItem = new Comment_Item();
                        JSONObject objec = jsonArray.getJSONObject(i);
                        commentItem.setComment_title(objec.getString("comment"));
                        commentItem.setSender_name(objec.getJSONObject("user").getString("username"));
                        commentItem.setUserid(objec.getJSONObject("user").getInt("id"));
                        helper.setSellerid(seller_id);
                        //commentItem.setTime_send(getCurrentTime(objec.getString("created_at")));
                        commentitems.add(commentItem);
                    }

                    adapter = new Comment_Adapter(commentitems, this);
                    commnetsRecycler.setVisibility(View.VISIBLE);
                    commnetsRecycler.setAdapter(adapter);
                } catch (JSONException e) {
                    removeSimpleProgressDialog();
                    e.printStackTrace();
                }

                if (details != null) {
                    adCity.setText(details.getCity());
                    adCity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent ina= new Intent(ADetailed.this,SearchBycity.class);
                            ina.putExtra("cidi",Integer.parseInt(details.getCityid()));
                            startActivity(ina);
                        }
                    });
                    adNumber.setText(String.valueOf(details.getAdId()));
                    adTitle.setText(details.getTitle());
                    adUserName.setText(details.getUsername());
                    adContent.setText(details.getContent());
                    adMobile.setText(details.getMobile());
                      adTime.setText(getCurrentTime(details.getCreatedDate()));
                   // checkIfAdInFavourite();
                   // checkIfFollowedComments();
                   // count_of_likes();
                    if (helper.getUserID() != null && details.getUserId() != null) {
                        if (details.getUserId().equals(helper.getUserID())) {
                            menubtn.setVisibility(View.VISIBLE);
                        }
                    }
                }
                removeSimpleProgressDialog();
                break;
            case Const.ServiceCode.SEND_EVALUATE:
                removeSimpleProgressDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject stat = jsonObject.getJSONObject("msg");
                    String msg = stat.getString("msg");
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case Const.ServiceCode.CHECK_IF_AD_FAVOURITE:
                removeSimpleProgressDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    favStatues = Integer.parseInt(jsonObject.getString("status"));
                    removeSimpleProgressDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (favStatues == 0) {
                    AdFavBtn.setImageResource(R.mipmap.hearticon);

                } else if (favStatues == 1) {
                    AdFavBtn.setImageResource(R.mipmap.hearticoncopy);
                }
                break;

            case Const.ServiceCode.POST_COMMENT:
                comment_model = new Comment_model();
                removeSimpleProgressDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("success")) {
                        Comment_Item item = new Comment_Item();
                        item.setSender_name(helper.getUserName());
                        item.setComment_title(maessa);
                        Date currentTime = Calendar.getInstance().getTime();
                        item.setTime_send(getCurrentTime(String.valueOf(currentTime)));
                        commentitems.add(item);
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    removeSimpleProgressDialog();
                    e.printStackTrace();
                }
                break;


            case Const.ServiceCode.PUT_AD_INFAVOURTIE:
                removeSimpleProgressDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject msg = jsonObject.getJSONObject("msg");
                    if (msg.getString("msg") != null)
                        favStatues = Integer.parseInt(msg.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (favStatues == 1) {
                    AdFavBtn.setImageResource(R.mipmap.hearticoncopy);
                } else {
                    AdFavBtn.setImageResource(R.mipmap.hearticon);
                }
                break;
            case Const.ServiceCode.REMOVE_AD_FROMFAVOURTIE:
                favStatues = 0;
                removeSimpleProgressDialog();

                break;
            case Const.ServiceCode.GET_ALLCOMMENT:

                break;
            case Const.ServiceCode.CHECK_IF_FOLLOW_COMMENTS:
                try {
                    removeSimpleProgressDialog();

                    JSONObject jsonObject = new JSONObject(response);
                    commentStatues = Integer.parseInt(jsonObject.getString("status"));
                } catch (JSONException e) {
                    removeSimpleProgressDialog();
                    e.printStackTrace();
                } finally {

                }
                if (commentStatues == 0) {
                    followIcon.setImageResource(R.drawable.subscribe);

                } else if (commentStatues == 1) {
                    followIcon.setImageResource(R.drawable.subscribed);

                }
                break;
            case Const.ServiceCode.FOLLOW_AD_COMMENTS:
                removeSimpleProgressDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    commentStatues = Integer.parseInt(jsonObject.getString("status"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;
            case Const.ServiceCode.REMOVE_FOLLOWING_AD_COMMENTS:
                removeSimpleProgressDialog();

                commentStatues = 0;

                // if (commentStatues == 0){
                Toast.makeText(this, getString(R.string.youareunfollowcomments), Toast.LENGTH_SHORT).show();
                followIcon.setImageResource(R.drawable.subscribe);

                break;
            case Const.ServiceCode.SEND_MESSAGE:
                removeSimpleProgressDialog();
             //   try {
                 //   JSONObject jsonObject = new JSONObject(response);
                  //  if (jsonObject.getBoolean("success")) {
                        Toast.makeText(this, getString(R.string.messagesent), Toast.LENGTH_SHORT).show();
                try {
                    if(!helper.getUserID().equals(seller_id)) {
                        sendNotification(getString(R.string.newmessage));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                    } else {
//                        Toast.makeText(this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//                    }
                    // messageStatues = Integer.parseInt(jsonObject.getString("status"));

//                } catch (JSONException e) {
//                    removeSimpleProgressDialog();
//                    e.printStackTrace();
//                }
//                if (messageStatues == 1) {
//                } else {
//                }
                break;
            default:
                break;
        }
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

    private void followComments() {
        if (helper.getAPI_TOKEN() != null) {

            JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("user_id",helper.getUserID());
            jsonObject.put("advertise_id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

            new Json_Controller().PostData2Server(this, "http://alsog.net/api/followers/add.json",
                    requestBody, new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) throws JSONException {
                        JSONObject jsonObject1 = new JSONObject(result);
                            JSONObject msg = jsonObject1.getJSONObject("msg");
                            String m = msg.getString("msg");
                            if(m.equals("1")){
                                commentStatues = 1;
                                Toast.makeText(ADetailed.this, getString(R.string.youarefollowcomments), Toast.LENGTH_SHORT).show();
                                followIcon.setImageResource(R.drawable.subscribed);
                            } else {
                                commentStatues = 0;
                                followIcon.setImageResource(R.drawable.subscribe);
                                Toast.makeText(ADetailed.this, getString(R.string.youareunfollowcomments), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(ADetailed.this, getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
                        }
                    },false);

//        if (helper.getAPI_TOKEN() != null) {
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put(Const.URL,
//                    "http://alsog.net/api/followers/add.json");
//            requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
//                    Const.ServiceCode.FOLLOW_AD_COMMENTS, this, this));
        }
    }

//    private void unfollowComments() {
//         HashMap<String, String> map = new HashMap<String, String>();
//        map.put(Const.URL,
//                "http://bazar.net.sa/api/v1/follow_comment/delete/"+id+"?api_token="+ helper.getAPI_TOKEN());
//        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
//                Const.ServiceCode.REMOVE_FOLLOWING_AD_COMMENTS, this, this));
//    }


    private void checkIfFollowedComments() {
        if (helper.getAPI_TOKEN() != null) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(Const.URL,
                    "http://bazar.net.sa/api/v1/follow_comment/check/" + id + "?api_token=" + helper.getAPI_TOKEN());
            requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                    Const.ServiceCode.CHECK_IF_FOLLOW_COMMENTS, this, this));
        }
    }

    private void openMessageDialog() {
        final Dialog mDialog = new Dialog(this, R.style.MyDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.message_dialog);

        final EditText tvTitle = (EditText) mDialog.findViewById(R.id.message_content);
        TextView titlee = (TextView) mDialog.findViewById(R.id.message_title);
        titlee.setText(getString(R.string.private_message) + " " + details.getUsername());
        Button btnCancel = (Button) mDialog.findViewById(R.id.cancel_send);
        final ImageButton close = (ImageButton) mDialog.findViewById(R.id.closedialog);
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


    private void openViolenceDialog() {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.report_screen);
        final RadioButton yes_btn = (RadioButton) dialog.findViewById(R.id.yes_btn);
        final RadioButton no_btn = (RadioButton) dialog.findViewById(R.id.no_btn);
        final ImageButton close = (ImageButton) dialog.findViewById(R.id.closedialog);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ImageButton send = (ImageButton) dialog.findViewById(R.id.sendreport);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (no_btn.isChecked()) {
                    dialog.dismiss();
                } else if (yes_btn.isChecked()) {
                    JSONObject oj = new JSONObject();
                    try {
                        oj.put("advertise_id", id);
                        oj.put("report", "");
                        oj.put("user_id", helper.getUserID());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final String requestbody = oj.toString();
                    new Json_Controller().PostData2Server(ADetailed.this, "http://alsog.net/api/reports/add.json",
                            requestbody, new VolleyCallback() {
                                @Override
                                public void onSuccess(String result) throws JSONException {
                                    JSONObject jsonObject = new JSONObject(result);
                                    JSONObject msg = jsonObject.getJSONObject("msg");
                                    if (msg.getString("msg").equals("your report has been sent")) {
                                        Toast.makeText(ADetailed.this, "تم الابلاغ عن هذا الاعلان", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(ADetailed.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(String error) {
                                    Toast.makeText(ADetailed.this, getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();

                                }
                            }, false);

//                    new Json_Controller().GetDataFromServer(new VolleyCallback() {
//                        @Override
//                        public void onSuccess(String result) {
//                            try {
//                                if (result != null) {
//                                    JSONObject main_obj = new JSONObject(result);
//                                    String status_json = main_obj.getString("status");
//
//
//                                } else {
//
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onError(String error) {
//                            removeSimpleProgressDialog();
//                            Toast.makeText(ADetailed.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
//                        }
//                    }, ADetailed.this, "http://bazar.net.sa/api/v1/report/post/" + id + "?api_token=" +  helper.getAPI_TOKEN(), false);

                }
            }

        });
        dialog.show();
    }

    private void openLoginDialog() {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.please_login);
        final TextView yes_btn = (TextView) dialog.findViewById(R.id.yess);
        final TextView no_btn = (TextView) dialog.findViewById(R.id.noo);
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ADetailed.this, Log_In.class);
                startActivity(intent);
            }
        });
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openEvaluateDialog() {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.evaluate);


        final RadioButton yes_btn = (RadioButton) dialog.findViewById(R.id.yes_btn);
        final RadioButton no_btn = (RadioButton) dialog.findViewById(R.id.no_btn);
        final ImageButton close = (ImageButton) dialog.findViewById(R.id.closedialog);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final EditText evaluate_text = (EditText) dialog.findViewById(R.id.evaluate_text);
        ImageButton send = (ImageButton) dialog.findViewById(R.id.sendreportz);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (no_btn.isChecked()) {
                    dialog.dismiss();
                    sendEvaluate(getString(R.string.nobuy) + " " + evaluate_text.getText().toString());

                } else if (yes_btn.isChecked()) {
                    dialog.dismiss();
                    sendEvaluate(getString(R.string.yesbuy) + " " + evaluate_text.getText().toString());

                }
            }

        });
        dialog.show();
    }

    private void openCommentDialog() {
        final Dialog mDialog = new Dialog(this, R.style.MyDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.comment_screen);
        final TextView title = (TextView) mDialog.findViewById(R.id.about);
        title.setText(getString(R.string.commentabout) + " " + details.getTitle());

        ImageButton close = (ImageButton) mDialog.findViewById(R.id.close_dialog);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        final EditText tvTitle = (EditText) mDialog.findViewById(R.id.the_comment);
        Button btnSend = (Button) mDialog.findViewById(R.id.send_comment);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = tvTitle.getText().toString();
                postComment(message);
                mDialog.dismiss();

            }
        });

        mDialog.show();

    }

    String maessa;

    private void postComment(String message) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL,
                "http://alsog.net/api/comments/add.json");
        map.put("advertise_id", id);
        map.put("user_id", helper.getUserID());
        map.put("comment", message);
        maessa = message;
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                Const.ServiceCode.POST_COMMENT, this, this, headers));
        try {
            if(!helper.getUserID().equals(seller_id)){
                sendNotification(getString(R.string.newcomment) + " " + message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Accept","application/json");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL,
                "http://alsog.net/api/chats/add.json");
        map.put("too", seller_id);
        map.put("fromm", helper.getUserID());
        map.put("msgs", message);
        map.put("username", helper.getUserName());
        map.put("userto", adUserName.getText().toString());

        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                Const.ServiceCode.SEND_MESSAGE, this, this,header));
    }



    private void sendEvaluate(String message) {
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Accept", "application/json");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL,
                "http://alsog.net/api/rates/add.json");
        map.put("user_id", details.getUserId());
        map.put("advertise_id", id);
        map.put("stars", message);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                Const.ServiceCode.SEND_EVALUATE, this, this, header));
    }

    private void getAllComments() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL,
                "http://bazar.net.sa/api/v1/comments/post/" + id);
        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                Const.ServiceCode.GET_ALLCOMMENT, this, this));
    }

    public void sendNotification(String message) throws JSONException {

        JSONArray tags = new JSONArray();
        JSONObject filters = new JSONObject();
        filters.put("field", "tag");
        filters.put("key", "id");
        filters.put("relation", "=");
        filters.put("value", details.getUserId());
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
                  //  Toast.makeText(ADetailed.this, result, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(ADetailed.this, error, Toast.LENGTH_LONG).show();
                    removeSimpleProgressDialog();
                }
            }, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.adEvaluation:
                if (!TextUtils.isEmpty(helper.getAPI_TOKEN())) {
                    openEvaluateDialog();
                } else {
                    openLoginDialog();
                }
                break;

            case R.id.adUserName:
                if (!TextUtils.isEmpty(helper.getAPI_TOKEN())) {
                    Intent in = new Intent(this, MyADS.class);
                    in.putExtra("userid", details.getUserId());
                    in.putExtra("adid",id);
                    startActivity(in);
                } else {
                    openLoginDialog();
                }

                break;
            case R.id.followLinear:
                if (!TextUtils.isEmpty(helper.getAPI_TOKEN())) {
                    if (commentStatues == 0) {
                        followComments();
                    } else {
                        followComments();
                    }
                } else {
                    openLoginDialog();
                }

                break;
            case R.id.adFavbtn:
                if (!TextUtils.isEmpty(helper.getAPI_TOKEN())) {
                    putAdToMyFavourite();

                } else {
                    openLoginDialog();
                }
                break;
            case R.id.messageLinear:
                if (!TextUtils.isEmpty(helper.getAPI_TOKEN())) {
                    openMessageDialog();
                } else {
                    openLoginDialog();
                }
                break;
            case R.id.adViolencebtn:
                if (!TextUtils.isEmpty(helper.getAPI_TOKEN())) {
                    openViolenceDialog();
                } else {
                    openLoginDialog();
                }
                break;
            case R.id.backbtn:
                finish();
                break;
            case R.id.sharebtn:
                if (!TextUtils.isEmpty(helper.getAPI_TOKEN())) {
                    showAlertDialog();
                } else {
                    openLoginDialog();
                }
                break;
            case R.id.commentLinear:
                if (!TextUtils.isEmpty(helper.getAPI_TOKEN())) {
                    openCommentDialog();
                } else {
                    openLoginDialog();
                }
                break;
            case R.id.callLinear:
                if (details != null) {
                    String uri = "tel:" + details.getMobile();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(uri));
//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
                    startActivity(intent);
                }
                break;

            default:
                break;
        }
    }

    public void refreshAd() {
        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                 ///   try {
                        //JSONObject jsonObject = new JSONObject(result);
                        Toast.makeText(ADetailed.this, getString(R.string.updated), Toast.LENGTH_LONG).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                } else {
                    Toast.makeText(ADetailed.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
                Toast.makeText(ADetailed.this, getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
            }
        }, this, "http://alsog.net/api/advertises/updateAds/" + id +".json", false);
    }


    private void deleteAd(String ad_id) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Delete_Ad> call = apiService.deleteAd(
                String.valueOf(ad_id));
        call.enqueue(new Callback<Delete_Ad>() {
            @Override
            public void onResponse(Call<Delete_Ad> call, final retrofit2.Response<Delete_Ad> response) {
                if (response.body() != null) {
                    if (response.body().isSuccess())
                    {
                    Toast.makeText(ADetailed.this, getString(R.string.delete_ad_success), Toast.LENGTH_LONG).show();
                    ADetailed.this.finish();
                    startActivity(new Intent(ADetailed.this,MainActivity.class));
                    }
                    else
                    Toast.makeText(ADetailed.this, getString(R.string.delete_ad_fail), Toast.LENGTH_LONG).show();

                }

                else {
                    Toast.makeText(ADetailed.this, getString(R.string.delete_ad_fail), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Delete_Ad> call, Throwable t) {
                Log.d("fail",call.toString());
                //   Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (receiver == null) {
            receiver = new Receiver();
            IntentFilter filter = new IntentFilter(BroadcastHelper.ACTION_NAME);
            this.registerReceiver(receiver, filter);
            isRecieverRegistered = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRecieverRegistered) {
            unregisterReceiver(receiver);
        }
    }

    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            //   Log.v("r", "receive " + arg1.getStringExtra(BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME));
            String methodName = arg1.getStringExtra(BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME);
            if (methodName != null && methodName.length() > 0) {
                //  Log.v("receive", methodName);
                switch (methodName) {
                    case "title":
                        ArrayList<Bitmap> images = new ArrayList<Bitmap>();
                        ArrayList<Parcelable> ImagesArray = (ArrayList<Parcelable>) arg1.getParcelableArrayListExtra("photos");
                        for (int i = 0; i < ImagesArray.size(); i++) {
                            images.add(i, (Bitmap) ImagesArray.get(i));
                        }
                        Intent intent = new Intent(ADetailed.this, NewGallery.class);
                        intent.putParcelableArrayListExtra("photos", images);
                        startActivity(intent);

                        break;


                }
            }
        }
    }


}
