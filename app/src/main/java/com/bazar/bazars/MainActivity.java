package com.bazar.bazars;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bazar.bazars.Adapters.ImageAdapter;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.MainMenuItem;
import com.bazar.bazars.Items.Menu_Items;
import com.bazar.bazars.Items.Tab_one_items;
import com.bazar.bazars.Items.Tab_three_item;
import com.bazar.bazars.Items.Tab_two_item;
import com.dision.android.rtlviewpager.RTLViewPager;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ArrayList<Menu_Items> menu_list;
    public static ArrayList<Tab_one_items> Cat_arrayList;
    public static ArrayList<Tab_two_item> SUB_arrayList;
    public static ArrayList<Tab_three_item> SUBSub_arrayList;
    public int pos;
    public Button button;
    PreferenceHelper helper;
    public static TabLayout tabLayout;
    RTLViewPager vp;
    private Spinner menupop;
    private ListView list;
    PopupMenu popup;
    boolean islogin = false,isLoaded = true;
    String username;
    Container_Fragment fragment;
    String my_links = "",my_link2="";
    ArrayList<MainMenuItem> menuItems = new ArrayList<MainMenuItem>();
    private Typeface lightFace;
    ArrayList<String> itemsStrings = new ArrayList<String>();
    final String[] selectedItemText = {""};
    TextView nonum,menum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        helper = new PreferenceHelper(this);
        OneSignal.startInit(this).setNotificationOpenedHandler(new ExampleNotificationOpenedHandler()).init();
        OneSignal.enableNotificationsWhenActive(true);
        if(helper.getUserID()!=null) {
            OneSignal.sendTag("id", helper.getUserID());
            OneSignal.sendTag("follow", helper.getUserID());
            setUserStat("1");
        }
         popppps();
       // vp2 = (ViewPager) findViewById(R.id.container_main);
        if (intent.getStringExtra("islogin") != null && intent.getStringExtra("username") != null) {
            islogin = true;
            username = intent.getStringExtra("username");
        }
        if (!TextUtils.isEmpty(helper.getAPI_TOKEN())) {
            islogin = true;
            username=helper.getUserName();
        }
        String l = helper.getAPI_TOKEN();
        fragment = new Container_Fragment();
        menupop = (Spinner) findViewById(R.id.menupop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        menu_list = new ArrayList<Menu_Items>();
        nonum = (TextView)findViewById(R.id.nonum);
        menum = (TextView)findViewById(R.id.menum);
            /////////////////********************************  toolbar ********************************************
            ImageButton button_filter = (ImageButton) findViewById(R.id.button_filter);
            button_filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    trans();
                    tabLayout.setVisibility(View.VISIBLE);
                    mViewPager.setCurrentItem(0);
                }
            });
//
            ImageButton button_search = (ImageButton) findViewById(R.id.button_search);
            button_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (helper.getAPI_TOKEN() != null) {
                        Intent intent = new Intent(MainActivity.this, Message.class);
                        startActivity(intent);
                    }else {
                        Intent intent1 = new Intent(MainActivity.this, Log_In.class);
                        startActivity(intent1);
                    }
                }
            });


//////////////////////////////////////////////////////////////////////////////////////////////////////////
            Iconify.with(new FontAwesomeModule()).with(new IoniconsModule());

            trans();


            ImageButton button_camera = (ImageButton) findViewById(R.id.search_btn);
            button_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, Search_activity.class);
                    startActivity(intent);
                }
            });


            ImageButton add_Advertisment = (ImageButton) findViewById(R.id.plus);
            add_Advertisment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (helper.getAPI_TOKEN() != null) {

                        Intent intent = new Intent(MainActivity.this, Qasam.class);
                        startActivity(intent);
                    }else {
                        Intent intent1 = new Intent(MainActivity.this, Log_In.class);
                        startActivity(intent1);
                    }
                }
            });

            ImageButton notification = (ImageButton) findViewById(R.id.alert);
            notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (helper.getAPI_TOKEN() != null) {
                        Intent intent = new Intent(MainActivity.this, Notifcation_activity.class);
                        startActivity(intent);
                    }else {
                        Intent intent1 = new Intent(MainActivity.this, Log_In.class);
                        startActivity(intent1);
                    }
                }
            });

            final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            boolean appear_notif[] = {false, false, false, false, false, false, false, false};
            String menu_notif[] = {"", "", "", "", "", "", "", ""};
            String[] menu_titles = getResources().getStringArray(R.array.menu_titles);
            int[] menu_images = {};
            for (int i = 0; i < menu_titles.length; i++) {
                menu_list.add(new Menu_Items(menu_titles[i], menu_notif[i], appear_notif[i]));
            }
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            drawer.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            GridView gridview = (GridView) findViewById(R.id.gridview);
            gridview.setAdapter(new ImageAdapter(getApplicationContext()));

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    ViewPager vp2 = (ViewPager) findViewById(R.id.container_main);

                    mViewPager.setCurrentItem(1);
                    if(vp2!= null) {
                        vp2.setCurrentItem(position + 1);
                    }

                    drawer.closeDrawers();
                }
            });





    }
//
//    {
//        "app_id": "a2d7b36a-6307-4e0d-9c69-d937c44a97a5",
//            "data": {
//        "foo": "bar"
//    },
//        "contents": {
//        "en": "تم حجز موعد جديد "
//    },
//        "filters": [
//        {
//            "field": "tag",
//                "key": "id",
//                "relation": "=",
//                "value": "1"
//        },
//        {
//            "operator": "AND"
//        },
//        {
//            "field": "tag",
//                "key": "adminid",
//                "relation": "=",
//                "value": "14"
//        }
//  ]
//    }

    private void askFormPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.CALL_PHONE)) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{ android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.WAKE_LOCK},
                            8);
                }
            }
        }
    }
    boolean active = false;

    private String SelectedSpinnerItemyear(ArrayList<String> spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(this, R.layout.newmenuitem, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.newmenuitem);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(this);

        return selectedItemText[0];
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setUserStat(String ons){

        JSONObject object = new JSONObject();
        try{
            object.put("state",ons);
        }catch (JSONException e){
            e.printStackTrace();
        }
        final String requestBody = object.toString();
        new Json_Controller().PostData2Server(this,
                "http://alsog.net/api/users/edit/" + helper.getUserID() + ".json",
                requestBody, new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) throws JSONException {
                    }

                    @Override
                    public void onError(String error) {

                    }
                },true);
    }

// This fires when a notification is opened by tapping on it or one is received while the app is running.

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(active) {
            if(position ==0){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(menuItems.get(0).getValue()));
                startActivity(browserIntent);
            }
            selectedItemText[0] = parent.getItemAtPosition(position).toString();
            if (selectedItemText[0].equals(getString(R.string.logout) + " " + helper.getUserName())) {
                helper.setAPI_TOKEN(null);
                helper.putUserID("");
                helper.putUserName("");
                setUserStat("0");
                active=false;
                popppps();
            } else if (selectedItemText[0].equals(getString(R.string.favads))) {
                if(helper.getAPI_TOKEN()!=null) {
                    Intent i = new Intent(MainActivity.this, Favorit_activity.class);
                    i.putExtra("link", helper.getAPI_TOKEN());
                    startActivity(i);
                }else {
                    Intent i = new Intent(MainActivity.this, Log_In.class);
                    startActivity(i);
                }
            } else if (selectedItemText[0].equals(getString(R.string.myads))) {
                if(helper.getAPI_TOKEN()!=null) {
                    Intent e = new Intent(MainActivity.this, MyADS.class);
                    e.putExtra("userid", helper.getUserID());
                    startActivity(e);
                }else {
                    Intent i = new Intent(MainActivity.this, Log_In.class);
                    startActivity(i);
                }
            } else if (selectedItemText[0].equals(getString(R.string.left_menu_text_login))) {
                Intent intent1 = new Intent(MainActivity.this, Log_In.class);
                startActivity(intent1);
            } else if (selectedItemText[0].equals(getString(R.string.register))) {
                Intent intent1 = new Intent(MainActivity.this, REGISTER_SCREEN.class);
                startActivity(intent1);
            } else {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(menuItems.get(position).getValue()));
                startActivity(browserIntent);
            }

        }else {
            active=true;
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler,OneSignal.PostNotificationResponseHandler {
        @Override
        public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
            try {
                if (additionalData != null) {
                    if (additionalData.has("actionSelected"))
                        Log.d("OneSignalExample", "OneSignal notification button with id " + additionalData.getString("actionSelected") + " pressed");
                    Toast.makeText(MainActivity.this,additionalData.toString(),Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this,Notifcation_activity.class);
                    startActivity(in);
                    BroadcastHelper.sendInform(MainActivity.this,"count");

                    Log.d("OneSignalExample", "Full additionalData:\n" + additionalData.toString());
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        @Override
        public void onSuccess(JSONObject response) {
            BroadcastHelper.sendInform(MainActivity.this,"count");

        }

        @Override
        public void onFailure(JSONObject response) {

        }
    }

    private class SpinnerAdapter extends ArrayAdapter<String> {

        ArrayList<String> arrayList = new ArrayList<String>();

        private SpinnerAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context, textViewResourceId, objects);
            this.arrayList = objects;
        }

        @Override
        public View getDropDownView(final int position, View convertView, ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            TextView tv = (TextView) view;
             tv.setTextColor(Color.BLACK);
            if(position==0){
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(menuItems.get(position).getValue()));
                        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(menuItems.get(position).getValue()));
                        PackageManager packageManager = getPackageManager();
                        if (i.resolveActivity(packageManager) != null) {
                            startActivity(i);
                        } else {
                            Toast.makeText(MainActivity.this, "sssss", Toast.LENGTH_SHORT).show();
                        }
                       // startActivity(i);
                    }
                });
            }
            return view;
        }

        @Override
        public boolean isEnabled(int position) {

            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

         View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View row = inflater.inflate(R.layout.menuitemimage, parent, false);
           // TextView label = (TextView) row.findViewById(R.id.spinner_textView);
            //label.setText(arrayList.get(position));
            //Log.i("ss", arrayList.get(position));
            //label.setTypeface(lightFace);

            return row;
        }
    }
    public void showTabLayout() {
        tabLayout.setVisibility(View.VISIBLE);

    }

    public void hideTabLayout() {
        tabLayout.setVisibility(View.GONE);

    }

    public void getMenuItems(){
        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                    Cat_arrayList = new ArrayList<Tab_one_items>();
                    try {

                        JSONObject main_obj = new JSONObject(result);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                // Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, MainActivity.this, "http://bazar.net.sa/api/v1/cat/json", false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setUserStat("0");
        if (isRecieverRegistered) {
            unregisterReceiver(internetConnectionReciever);
        }
        if(ia){
            if(receiver != null){
                unregisterReceiver(receiver);
            }
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



        public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(final int position) {
            Log.i("position", String.valueOf(position));
            if(Cat_arrayList != null) {
                if (Cat_arrayList.get(position).getSubCategories().size() > 0) {
                    return Tab_Two.newInstance(position, Cat_arrayList.get(position).getId());
                } else {
                    return Container_Fragment.newInstance(Integer.parseInt(Cat_arrayList.get(position).getId()), "1");
                }
            }else {
                return Container_Fragment.newInstance(0, "1");
            }
        }

        @Override
        public int getCount() {
            return Cat_arrayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Cat_arrayList.get(position).getName();
        }
    }
    private AlertDialog internetDialog;
    private boolean isNetDialogShowing = false, isRecieverRegistered = false;
    public BroadcastReceiver internetConnectionReciever = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo activeWIFIInfo = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (activeWIFIInfo.isConnected() || activeNetInfo.isConnected()) {
                removeInternetDialog();
                if(isLoaded){
                    trans();
                }
            } else {
                if (isNetDialogShowing) {
                    return;
                }
                showInternetDialog();
            }
        }
    };

    public void popppps() {
        showSimpleProgressDialog(this,"",getString(R.string.pleasewait),false);
        menuItems.clear();
        itemsStrings.clear();
        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                removeInternetDialog();
                try {
                    if (result != null) {
                        JSONObject main_obj = new JSONObject(result);
                        JSONObject lkal = main_obj.getJSONObject("call");
                        Iterator<String> iterator = lkal.keys();

                        while (iterator.hasNext()) {
                            MainMenuItem item = new MainMenuItem("","");
                            String key = iterator.next();
                            String value = lkal.optString(key);
                            item.setName(key);
                            item.setValue(value);
                             menuItems.add(item);
                        }
                        menuItems.add(new MainMenuItem(getString(R.string.favads),""));
                        menuItems.add(new MainMenuItem(getString(R.string.myads),""));
                        if(helper.getAPI_TOKEN()!=null){
                            menuItems.add(new MainMenuItem(getString(R.string.logout)+ " " + helper.getUserName(),""));

                        }else {
                            menuItems.add(new MainMenuItem(getString(R.string.register), ""));
                            menuItems.add(new MainMenuItem(getString(R.string.left_menu_text_login), ""));

                        }
                        for(int i =0;i<menuItems.size();i++){
                            String d = menuItems.get(i).getName();
                            itemsStrings.add(d);
                        }
                    }
                } catch (JSONException e) {
                    removeSimpleProgressDialog();
                    e.printStackTrace();
                }
                SelectedSpinnerItemyear(itemsStrings,menupop,0);
                removeSimpleProgressDialog();

            }
            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
                Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, MainActivity.this, "http://alsog.net/api/advertises/links.json", false);
    }

    Receiver receiver;
    boolean ia;
    @Override
    protected void onResume() {
        super.onResume();
        if(!isRecieverRegistered) {
            registerReceiver(internetConnectionReciever, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            isRecieverRegistered = true;
        }
        if (receiver == null) {
            receiver = new Receiver();
            IntentFilter filter = new IntentFilter(BroadcastHelper.ACTION_NAME);
            this.registerReceiver(receiver, filter);
            ia = true;
        }
        if(helper.getUserID()!= null){
           // if(helper.getOpenNotificationActivity().equals("1")){
                getNotificatinoNubmer();
               // helper.setOpenNotificationActivity("0");
           // }
        }
        active=false;
        askFormPermissions();
        SelectedSpinnerItemyear(itemsStrings,menupop,0);
    }

    private void getNotificatinoNubmer() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", helper.getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestbody = jsonObject.toString();
        new Json_Controller().PostData2Server(this, "http://alsog.net/api/notifcations/count.json", requestbody, new VolleyCallback() {
            @Override




            public void onSuccess(String result) throws JSONException {
                JSONObject object = new JSONObject(result);
                int l = object.getInt("countnotifcation");
                if (l != 0) {
                    nonum.setText(String.valueOf(l));
                    nonum.setVisibility(View.VISIBLE);
                } else {
                    nonum.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, "dwwdd", Toast.LENGTH_SHORT).show();
            }
        }, false);
    }
//        new Json_Controller().GetDataFromServer(new VolleyCallback() {
//            @Override
//            public void onSuccess(String result) throws JSONException {
//
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//        },this,"/" + helper.getUserID(),false);
//    }

    private void showInternetDialog() {
        removeSimpleProgressDialog();
        isNetDialogShowing = true;
        AlertDialog.Builder internetBuilder = new AlertDialog.Builder(
                MainActivity.this);
        internetBuilder.setCancelable(false);
        internetBuilder
                .setTitle(getString(R.string.dialog_no_internet))
                .setMessage(getString(R.string.dialog_no_inter_message))
                .setPositiveButton(getString(R.string.dialog_enable_3g),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // continue with delete
                                Intent intent = new Intent(
                                        Settings.ACTION_SETTINGS);
                                startActivity(intent);
                                removeInternetDialog();
                            }
                        })
                .setNeutralButton(getString(R.string.dialog_enable_wifi),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // User pressed Cancel button. Write
                                // Logic Here
                                startActivity(new Intent(
                                        Settings.ACTION_WIFI_SETTINGS));
                                removeInternetDialog();
                            }
                        })
                .setNegativeButton(getString(R.string.dialog_exit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                                removeInternetDialog();
                                finish();
                            }
                        });
        internetDialog = internetBuilder.create();
        internetDialog.show();
    }
    private void removeInternetDialog() {
        if (internetDialog != null && internetDialog.isShowing()) {
            internetDialog.dismiss();
            isNetDialogShowing = false;
            internetDialog = null;

        }
    }


    private void trans() {
        showSimpleProgressDialog(MainActivity.this,"",getString(R.string.pleasewait),false);

        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                    Cat_arrayList = new ArrayList<Tab_one_items>();

                    try {
                        SUB_arrayList = new ArrayList<Tab_two_item>();
                        JSONObject main_obj = new JSONObject(result);
                        JSONArray prices = main_obj.getJSONArray("data");
                        Cat_arrayList.add(0, new Tab_one_items("0", "الكل",  SUB_arrayList));

                        for (int index = 0; index < prices.length(); index++) {
                            JSONObject catgrey = prices.getJSONObject(index);
                            Log.i("name2", String.valueOf(catgrey));
                            String catname = catgrey.getString("name");
                            String catid = catgrey.getString("id");
                            if (catgrey.has("sub_categories")) {
                                JSONArray catsub = catgrey.getJSONArray("sub_categories");
                                SUB_arrayList = new ArrayList<Tab_two_item>();

                                for (int photosIndex = 0; photosIndex < catsub.length(); photosIndex++) {
                                    JSONObject dateObj = catsub.getJSONObject(photosIndex);
                                    String subname = dateObj.getString("name");
                                    String subid = dateObj.getString("id");
                                    if (dateObj.has("sub_mini_categories")) {
                                        JSONArray catsubsub = dateObj.getJSONArray("sub_mini_categories");
                                        SUBSub_arrayList = new ArrayList<Tab_three_item>();
                                        for (int photosIndexs = 0; photosIndexs < catsubsub.length(); photosIndexs++) {
                                            JSONObject dateObjs = catsubsub.getJSONObject(photosIndexs);
                                            String subnames = dateObjs.getString("name");
                                            String subids = dateObjs.getString("id");
                                            SUBSub_arrayList.add(new Tab_three_item(subids, subnames));
                                        }
                                    }
                                    SUB_arrayList.add(new Tab_two_item(subid, subname,  SUBSub_arrayList));
                                }
                            }
                            Cat_arrayList.add(new Tab_one_items(catid, catname,  SUB_arrayList));

                        }

                        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                        mViewPager = (ViewPager) findViewById(R.id.container);

                        if (mViewPager != null) {
                            mViewPager.setAdapter(mSectionsPagerAdapter);
                        }
                        removeSimpleProgressDialog();
                        tabLayout = (TabLayout) findViewById(R.id.tabs);
                        if (tabLayout != null) {
                            tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                            tabLayout.setupWithViewPager(mViewPager);

                        }

                        tabLayout.setOnTabSelectedListener(
                                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
                                    @Override
                                    public void onTabSelected(TabLayout.Tab tab) {
                                        super.onTabSelected(tab);

                                    }
                                });


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
               // Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, MainActivity.this, "http://alsog.net/api/categories", false);


    }

    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            //   Log.v("r", "receive " + arg1.getStringExtra(BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME));
            String methodName = arg1.getStringExtra(BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME);
            if (methodName != null && methodName.length() > 0) {
                //  Log.v("receive", methodName);
                switch (methodName) {
                    case "count":
                         getNotificatinoNubmer();
                        break;


                }
            }}
    }

}
