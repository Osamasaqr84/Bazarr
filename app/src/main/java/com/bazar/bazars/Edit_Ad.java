package com.bazar.bazars;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bazar.bazars.Connect_TO_Server.ApiService;
import com.bazar.bazars.Connect_TO_Server.FileUtils;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.Ad_photo_item;
import com.bazar.bazars.Items.Cities_items;
import com.bazar.bazars.Items.Edit_item;
import com.bazar.bazars.Items.Edit_photo_item;
import com.bazar.bazars.Items.Tab_one_items;
import com.bazar.bazars.Items.Tab_three_item;
import com.bazar.bazars.Items.Tab_two_item;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.PhotoLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Edit_Ad extends AppCompatActivity implements View.OnClickListener, com.android.volley.Response.ErrorListener, AsyncTaskCompleteListener {

      String title = "",mobile = "",content = "",cityid="",
              subid="",subsubid="",maincatid="",year="",adid="";
    PreferenceHelper helper;
     TextView readd,editfields,editcat;
    EditText editTitle,editContent,editPhone;
    RecyclerView photos;
    Edit_photo_adapter adapter;
    ArrayList<Edit_photo_item> items;
    ImageView btnPickImage;
    int pos;
    private RequestQueue requestQueue;
    Receiver receivera;
    private Spinner cat_main, city_spinner, country_spinner, spinner_child2, yearsspin;
    public static ArrayList<Tab_one_items> Cat_arrayList;
    public static ArrayList<Tab_two_item> SUB_arrayList;
    public static ArrayList<Tab_three_item> SUBSub_arrayList;
    public static ArrayList<Tab_two_item> SUBSub_arrayList2;
    public static ArrayList<Cities_items> city_array;
    public static ArrayList<String> city_arrayval;
    public static ArrayList<String> main_cat_arr;
    public static ArrayList<String> subsub;
    public static ArrayList<String> spinnerchild2;
    private Typeface mediumFace, lightFace;
    private String idz;
     private HashMap<String, String> get_cityID_map, main_cat, sub_cat, subsub_cat, year_cat;
    boolean isRecieverRegistered = false;
    GalleryPhoto galleryPhoto;
    ScrollView fields,categoryies;
    MultipartRequest requests;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__ad);
        helper = new PreferenceHelper(this);
          readd = (TextView)findViewById(R.id.read_ad);
         editfields = (TextView)findViewById(R.id.editfields);
        editcat = (TextView)findViewById(R.id.editcat);
        fields = (ScrollView)findViewById(R.id.fields);
        galleryPhoto = new GalleryPhoto(getApplicationContext());
        country_spinner = (Spinner) findViewById(R.id.spinner_child1);
        city_spinner = (Spinner) findViewById(R.id.city_spiiner);
        cat_main = (Spinner) findViewById(R.id.cat_main);
        requestQueue = Volley.newRequestQueue(this);
         yearsspin = (Spinner) findViewById(R.id.spinner_model_selection);
        //imageViews = new ArrayList<Bitmap>();
        //activity = new Edit_Ad();
        String token = helper.getAPI_TOKEN();
        trans();
        transcity();
        showSimpleProgressDialog(this,"",getString(R.string.pleasewait),false);
         spinner_child2 = (Spinner) findViewById(R.id.spinner_child2);
        spinnerchild2 = new ArrayList<String>();
        spinnerchild2.add("إختـر القسم الفرعي");
        categoryies = (ScrollView)findViewById(R.id.categories);
        editfields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 editcat.setBackgroundColor(getResources().getColor(R.color.white));
                editfields.setBackgroundColor(getResources().getColor(R.color.light_gray));
                categoryies.setVisibility(View.GONE);
                fields.setVisibility(View.VISIBLE);
            }
        });
        editcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editfields.setBackgroundColor(getResources().getColor(R.color.white));
                editcat.setBackgroundColor(getResources().getColor(R.color.light_gray));
                fields.setVisibility(View.GONE);
                categoryies.setVisibility(View.VISIBLE);
            }
        });
        editTitle = (EditText)findViewById(R.id.title);
        editPhone = (EditText)findViewById(R.id.mobile);
        editContent = (EditText)findViewById(R.id.typeclass);
        photos = (RecyclerView)findViewById(R.id.photo_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        photos.setLayoutManager(manager);
        btnPickImage = (ImageView)findViewById(R.id.IvCamera);
        photos.setHasFixedSize(true);
        photos.setNestedScrollingEnabled(true);
        items = new ArrayList<Edit_photo_item>();

        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
            }
        });
        readd.setOnClickListener(this);
         Intent intent = getIntent();
        if(intent!=null){
          adid = intent.getStringExtra("adid");
           maincatid = intent.getStringExtra("cat1");
            subid = intent.getStringExtra("cat2");
            subsubid = intent.getStringExtra("cat3");
            cityid = intent.getStringExtra("city");
        }
         transs(adid);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (receivera == null) {
            receivera = new Receiver();
            IntentFilter filter = new IntentFilter(BroadcastHelper.ACTION_NAME);
                 registerReceiver(receivera, filter);
            isRecieverRegistered = true;
        }
        askFormPermissions();

    }

    private void askFormPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(Edit_Ad.this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Edit_Ad.this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(Edit_Ad.this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    ActivityCompat.requestPermissions(Edit_Ad.this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.WAKE_LOCK},
                            8);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isRecieverRegistered){
            unregisterReceiver(receivera);
        }
    }

    ArrayList<Ad_photo_item> images = new ArrayList<Ad_photo_item>();
    private void transs(String id) {
        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray datas = jsonObject.getJSONArray("data");
                        JSONObject data = datas.getJSONObject(0);

                        Edit_item item = new Edit_item();
                        item.setId(data.getString("id"));
                        item.setContent(data.getString("content"));
                        item.setMobile(data.getString("mobile"));
                        item.setTitle(data.getString("name"));
                        editContent.setText(item.getContent());
                        editPhone.setText(item.getMobile());
                        editTitle.setText(item.getTitle());
                        JSONArray array = data.getJSONArray("photos");
                            for (int i = 0; i < array.length(); i++) {
                                Edit_photo_item ll = new Edit_photo_item();
                                JSONObject ao = array.getJSONObject(i);
                                if(array.get(i).toString().length()>4) {
                                    ll.setImage("http://alsog.net/library/photos/" + ao.getString("photo"));
                                    items.add(ll);
                                }
                            }

                        item.setImages(items);
                        adapter = new Edit_photo_adapter(Edit_Ad.this,items);
                        photos.setAdapter(adapter);
                        if(items.size()>=1) {
                            photos.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(Edit_Ad.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
             }

            @Override
            public void onError(String error) {
                //Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(Edit_Ad.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, Edit_Ad.this, "http://alsog.net/api/advertises/adsdetails/" + id , false);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if(data.getClipData() == null){
                    try {
                        Edit_photo_item photoItem = new Edit_photo_item();
                        android.net.Uri itm = data.getData();
                        galleryPhoto.setPhotoUri(itm);
                        String photoPath = galleryPhoto.getPath();
                        try {
                            Bitmap bitmap = PhotoLoader.init().from(photoPath).getBitmap();
                            FileOutputStream bmpFile = new FileOutputStream(photoPath);
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bmpFile);
                             ImageView imageView = new ImageView(this);
                            LinearLayout.LayoutParams layoutParams =
                                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.MATCH_PARENT);
                            imageView.setLayoutParams(layoutParams);
                            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            imageView.setPadding(0, 0, 0, 10);
                            imageView.setAdjustViewBounds(true);
                            imageView.setImageBitmap(bitmap);
                            //fileuris.add(itm);
                           // photoItem.setImage(itm.toString());
                            photoItem.setRui(itm);
                            //imageViews.add(bitmap);
                            items.add(photoItem);
//                            adapter = new Edit_photo_adapter(this,items);
//                            photos.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            if(items.size()>=1){
                                photos.setVisibility(View.VISIBLE);
                            }
                        } catch (FileNotFoundException e) {
                                Toast.makeText(this, "Error while loading image", Toast.LENGTH_SHORT).show();

                        }
                    }finally {

                    }

                }else {
                    if (data.getClipData() != null)
                        for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                            ClipData.Item item = data.getClipData().getItemAt(i);
                            Edit_photo_item photoItem = new Edit_photo_item();
                            galleryPhoto.setPhotoUri(item.getUri());
                            String photoPath = galleryPhoto.getPath();

                            try {
                                Bitmap bitmap = PhotoLoader.init().from(photoPath).getBitmap();
                                FileOutputStream bmpFile = new FileOutputStream(photoPath);
                                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bmpFile);
                                 ImageView imageView = new ImageView(this);
                                LinearLayout.LayoutParams layoutParams =
                                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.MATCH_PARENT);
                                imageView.setLayoutParams(layoutParams);
                                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                imageView.setPadding(0, 0, 0, 10);
                                imageView.setAdjustViewBounds(true);
                                imageView.setImageBitmap(bitmap);
                                photoItem.setRui(item.getUri());

                              //  fileuris.add(item.getUri());
                                //photoItem.setImage(item.getUri().toString());
                                //linearMain.addView(imageView);
                                items.add(photoItem);
                                //imageViews.add(bitmap);

                            } catch (FileNotFoundException e) {
                                Toast.makeText(this, "Error while loading image", Toast.LENGTH_SHORT).show();

                            }
                        }
                    if(items.size()>=1){
                        photos.setVisibility(View.VISIBLE);
                    }
//                    adapter = new Edit_photo_adapter(this,items);
//                    photos.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        }
        else
        {

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




    private void doFileUpload(){
        if(editTitle.getText().length()<1){
            Toast.makeText(this,getString(R.string.entertitle), Toast.LENGTH_SHORT).show();
            return;
        }else if(editPhone.getText().length()<1){
            Toast.makeText(this,getString(R.string.enterphone), Toast.LENGTH_SHORT).show();
            return;
        }else if(editContent.getText().length()<1){
            Toast.makeText(this,getString(R.string.entertarget), Toast.LENGTH_SHORT).show();
            return;
        }
        showSimpleProgressDialog(Edit_Ad.this,"",getString(R.string.pleasewait),false);
        if(city_spinner.getSelectedItem() != city_spinner.getItemAtPosition(0)){
            cityid = get_cityID_map.get(city_spinner.getSelectedItem().toString());
        }

        if(!spinner_child2.isShown()){
            if(country_spinner.isShown()) {
                if (country_spinner.getSelectedItem() != country_spinner.getItemAtPosition(0)) {
                    subid = sub_cat.get(country_spinner.getSelectedItem().toString());
                }
            }

            if(cat_main.getSelectedItem() != cat_main.getItemAtPosition(0)) {
                maincatid = main_cat.get(cat_main.getSelectedItem().toString());
            }
            if(yearsspin.isShown()) {
                if (yearsspin.getSelectedItemPosition() == 0) {
                    year = "0";
                } else if (yearsspin.getSelectedItem() == null) {
                    year = "0";

                } else {
                    year = yearsspin.getSelectedItem().toString();

                }
            }

        }else{
            if(country_spinner.getSelectedItem()!=country_spinner.getItemAtPosition(0)) {
                subid = sub_cat.get(country_spinner.getSelectedItem().toString());
            }
            if(spinner_child2.getSelectedItem()!= spinner_child2.getItemAtPosition(0)) {
                subsubid = subsub_cat.get(spinner_child2.getSelectedItem().toString());
            }
            if(cat_main.getSelectedItem() != cat_main.getItemAtPosition(0)) {
                maincatid = main_cat.get(cat_main.getSelectedItem().toString());
            }
            if(yearsspin.getSelectedItem() != yearsspin.getItemAtPosition(0)) {
                year= yearsspin.getSelectedItem().toString();
             }
        }

        Retrofit.Builder  builder = new Retrofit.Builder().baseUrl("http://alsog.net/api/advertises/edit/" + adid + "/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        List<MultipartBody.Part> parts = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            parts.add(prepareFilePart("" + i ,items.get(i).getRui()));
        }
        Intent intent = getIntent();
//        String cityid = intent.getStringExtra("city_id");
//        String subid = intent.getStringExtra("sub_id");
//        String subsubid = intent.getStringExtra("subsub_id");
//        String maincatid = intent.getStringExtra("cat_id");
//        String year = intent.getStringExtra("year");
        if(year == null){
            year = "0";
        }
        if(subsubid == null){
            subsubid="0";
        }
        Log.i("cityid", cityid);
        ApiService client = retrofit.create(ApiService.class);
        if(maincatid == null){
            Toast.makeText(Edit_Ad.this,getString(R.string.entertitle), Toast.LENGTH_SHORT).show();
            return;
        }
        showSimpleProgressDialog(Edit_Ad.this,"",getString(R.string.adding),false);
        {
            Call<ResponseBody> call = client.edit(
                    createPartFromString(maincatid),
                    createPartFromString(subid),
                    createPartFromString(subsubid),
                    createPartFromString(cityid),
                    createPartFromString(editTitle.getText().toString()),
                    createPartFromString(editContent.getText().toString()),
                    createPartFromString(helper.getUserID()),
                    createPartFromString(editPhone.getText().toString()),
                    parts
            );

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    String serverResponse = null;
//                    try {
//                     //   serverResponse = response.body().string();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }


                    //Log.i("call", "onResponse: " + response.body().string());
                    removeSimpleProgressDialog();
                    Toast.makeText(Edit_Ad.this, getString(R.string.addsucc), Toast.LENGTH_SHORT).show();
                    finish();
                    Log.v("Upload", String.valueOf(serverResponse));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    removeSimpleProgressDialog();
                    Log.e("Upload error:", t.getMessage());
                    Log.i("respone", "onResponse: " + t.getMessage());
                }
            });
        }
    }
    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MultipartBody.FORM, descriptionString);
    }


    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {

        File file = FileUtils.getFile(this, fileUri);

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(fileUri)),
                        file
                );
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
    public class DataPart {
        private String fileName;
        private byte[] content;
        private String type;

        /**
         * Default data part
         */
        public DataPart() {
        }

        /**
         * Constructor with data.
         *
         * @param name label of data
         * @param data byte data
         */
        public DataPart(String name, byte[] data) {
            fileName = name;
            content = data;
        }

        /**
         * Constructor with mime data type.
         *
         * @param name     label of data
         * @param data     byte data
         * @param mimeType mime data like "image/jpeg"
         */
        public DataPart(String name, byte[] data, String mimeType) {
            fileName = name;
            content = data;
            type = mimeType;
        }

        /**
         * Getter file name.
         *
         * @return file name
         */
        public String getFileName() {
            return fileName;
        }

        /**
         * Setter file name.
         *
         * @param fileName string file name
         */
        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        /**
         * Getter content.
         *
         * @return byte file data
         */
        public byte[] getContent() {
            return content;
        }

        /**
         * Setter content.
         *
         * @param content byte file data
         */
        public void setContent(byte[] content) {
            this.content = content;
        }

        /**
         * Getter mime type.
         *
         * @return mime type
         */
        public String getType() {
            return type;
        }

        /**
         * Setter mime type.
         *
         * @param type mime type
         */
        public void setType(String type) {
            this.type = type;
        }
    }

    private void trans() {
        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                    Cat_arrayList = new ArrayList<Tab_one_items>();
                    city_array = new ArrayList<Cities_items>();
                    main_cat = new HashMap<String, String>();
                    sub_cat = new HashMap<String, String>();
                    subsub_cat = new HashMap<String, String>();
                    main_cat_arr = new ArrayList<String>();
                    main_cat_arr.add("اختر القسم");

                    try {

                        JSONObject main_obj = new JSONObject(result);
                        JSONArray prices = main_obj.getJSONArray("data");

                        Cat_arrayList.add(0, new Tab_one_items("0", "الكل",  SUB_arrayList));

                        for (int index = 0; index < prices.length(); index++) {

                            JSONObject catgrey = prices.getJSONObject(index);
                            JSONObject catgreys = catgrey.getJSONObject("Categories");

                            Log.i("name2", String.valueOf(catgrey));
                            String catname = catgreys.getString("name");
                            String catid = catgreys.getString("id");
                            String catorder = catgreys.getString("order");


                            if (catgreys.has("SubCategories")) {

                                JSONArray catsub = catgreys.getJSONArray("SubCategories");
                                SUB_arrayList = new ArrayList<Tab_two_item>();

                                for (int photosIndex = 0; photosIndex < catsub.length(); photosIndex++) {

                                    JSONObject dateObj = catsub.getJSONObject(photosIndex);
                                    String subname = dateObj.getString("name");
                                    String subid = dateObj.getString("id");
                                    SUBSub_arrayList = new ArrayList<Tab_three_item>();
                                    if (dateObj.has("SubCategories")) {

                                        JSONArray catsubsub = dateObj.getJSONArray("SubCategories");
                                        if (!catsubsub.equals(null)) {


                                            for (int photosIndexs = 0; photosIndexs < catsubsub.length(); photosIndexs++) {

                                                JSONObject dateObjs = catsubsub.getJSONObject(photosIndexs);
                                                String subnames = dateObjs.getString("name");
                                                String subids = dateObjs.getString("id");
                                                SUBSub_arrayList.add(new Tab_three_item(subids, subnames));

                                                subsub_cat.put(subnames, subids);
                                            }
                                        }
                                        if (index == 0) {
//                                            city_array.add(subname);

                                        }
                                    }

                                    SUB_arrayList.add(new Tab_two_item(subid, subname,  SUBSub_arrayList));
                                    sub_cat.put(subname, subid);
                                }
                            }

                            main_cat_arr.add(catname);
                            main_cat.put(catname, catid);
                            Cat_arrayList.add(new Tab_one_items(catid, catname,  SUB_arrayList));

                        }
                        CountrySpinnerItem(city_array, country_spinner, 1);

                        SelectedSpinnerItem(main_cat_arr, cat_main, 1);
                         removeSimpleProgressDialog();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        removeSimpleProgressDialog();
                        Toast.makeText(Edit_Ad.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    removeSimpleProgressDialog();
                    Toast.makeText(Edit_Ad.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
                // Toast.makeText(Add_category.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(Edit_Ad.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, Edit_Ad.this, "http://bazar.net.sa/api/v1/cat/json", false);


    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode){
            case 70:
                try{
                    JSONObject object = new JSONObject(response);

                    finish();
                } catch (JSONException e) {
                }
                break;
            default:
                break;
        }
    }

    private class SpinnerAdapter extends ArrayAdapter<String> {

        ArrayList<String> arrayList = new ArrayList<String>();

        public SpinnerAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context, textViewResourceId, objects);
            arrayList = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            TextView tv = (TextView) view;
            tv.setTypeface(lightFace);
            if (position == 0) {
                tv.setTextColor(Color.GRAY);
            } else {
                tv.setTextColor(Color.BLACK);
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

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View row = inflater.inflate(R.layout.spinner_item, parent, false);
            TextView label = (TextView) row.findViewById(R.id.spinner_textView);
            label.setText(arrayList.get(position));
            Log.i("ss", arrayList.get(position));
            label.setTypeface(lightFace);

            return row;
        }
    }
    private String SelectedSpinnerItemyear(ArrayList spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(this, R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                if (position > 0) {

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return selectedItemText[0];
    }

    private String SelectedSpinnerItem(ArrayList spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(this, R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemText[0] = parent.getItemAtPosition(position).toString();

                if (selectedItemText[0].equals("سيارات") || selectedItemText[0].equals("أجهزة")) {

                    spinner_child2.setVisibility(View.VISIBLE);
                } else {
                    spinner_child2.setVisibility(View.GONE);
                    yearsspin.setVisibility(View.GONE);
                }
                if (cat_main.getSelectedItem().equals("غير مصنف")) {
                    spinner_child2.setVisibility(View.GONE);
                }
                if (position == 1) {
                    SUBSub_arrayList2 = new ArrayList<Tab_two_item>();
                    spinnerchild2 = new ArrayList<String>();
                    spinnerchild2.add("إختـر القسم الفرعى");

                    for (int catsub = 0; catsub < Cat_arrayList.get(position).getSubCategories().size(); catsub++) {
                        String subnames = Cat_arrayList.get(position).getSubCategories().get(catsub).getName();
                        String subid = Cat_arrayList.get(position).getSubCategories().get(catsub).getId();
                        ArrayList<Tab_three_item> subsub = Cat_arrayList.get(position).getSubCategories().get(catsub).getSubCategoriess();

                        Log.i("subnames", subnames);
                        spinnerchild2.add(subnames);
                        SUBSub_arrayList2.add(new Tab_two_item(subid, subnames,  subsub));

                    }
                    CarsSpinnerItem2(spinnerchild2, country_spinner, 2);
                }else


                if (position > 0) {
                    if (cat_main.getSelectedItem().equals("غير مصنف")) {
                        spinner_child2.setVisibility(View.GONE);
                    } else {
                        SUBSub_arrayList2 = new ArrayList<Tab_two_item>();
                        spinnerchild2 = new ArrayList<String>();
                        spinnerchild2.add("إختـر القسم الفرعى");
                        for (int catsub = 0; catsub < Cat_arrayList.get(position).getSubCategories().size(); catsub++) {
                            String subnames = Cat_arrayList.get(position).getSubCategories().get(catsub).getName();
                            String subid = Cat_arrayList.get(position).getSubCategories().get(catsub).getId();
                            ArrayList<Tab_three_item> subsub = Cat_arrayList.get(position).getSubCategories().get(catsub).getSubCategoriess();
                            Log.i("subnames", subnames);
                            spinnerchild2.add(subnames);
                            SUBSub_arrayList2.add(new Tab_two_item(subid, subnames,  subsub));
                        }
                        SelectedSpinnerItem2(spinnerchild2, country_spinner, 2);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return selectedItemText[0];
    }
    private String CountrySpinnerItem(ArrayList spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(this, R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemText[0] = parent.getItemAtPosition(position).toString();

                if (selectedItemText[0].equals("سيارات") || selectedItemText[0].equals("أجهزة")) {

                    spinner_child2.setVisibility(View.VISIBLE);
                } else {
                    spinner_child2.setVisibility(View.GONE);


                }
                if (position > 0) {

                    SUBSub_arrayList2 = new ArrayList<Tab_two_item>();
                    spinnerchild2 = new ArrayList<String>();
                    spinnerchild2.add("إختـر القسم الفرعى");
                    spinner_child2.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return selectedItemText[0];
    }

    @Override
    protected void onPause() {
        super.onPause();
        boolean d = isRecieverRegistered;
    }

    private String SelectedSpinnerItem3(ArrayList spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(this, R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return selectedItemText[0];
    }

    private String SelectedSpinnerItem2(ArrayList<String> spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(this, R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    selectedItemText[0] = parent.getItemAtPosition(position).toString();
                    Log.i("SUBSub_arrayList", String.valueOf(SUBSub_arrayList2.get(0).getName()));
                    //  Toast.makeText(Add_category.this, "selected " + SUBSub_arrayList2.get(position - 1).getId() + SUBSub_arrayList2.get(position - 1).getName(), Toast.LENGTH_SHORT).show();


                    if (!SUBSub_arrayList2.get(position - 1).getSubCategoriess().isEmpty()) {
                        spinner_child2.setVisibility(View.VISIBLE);

                        subsub = new ArrayList<String>();
                        subsub.add("إختـر النوع");

                        for (int catsub = 0; catsub < SUBSub_arrayList2.get(position - 1).getSubCategoriess().size(); catsub++) {
                            String subnames = SUBSub_arrayList2.get(position - 1).getSubCategoriess().get(catsub).getName();
                            String subid = SUBSub_arrayList2.get(position - 1).getSubCategoriess().get(catsub).getId();
                            String suborder = SUBSub_arrayList2.get(position - 1).getSubCategoriess().get(catsub).getOrder();
                            subsub.add(subnames);

                        }
                        SelectedSpinnerItem3(subsub, spinner_child2, 2);
                        ArrayList<String> years = new ArrayList<String>();
                        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
                        years.add(0,"كل الاعوام");
                        for (int i = 1990; i <= thisYear; i++) {
                            years.add(Integer.toString(i));
                        }

                        // SelectedSpinnerItemyear(years, yearsspin, 1);
                        yearsspin.setVisibility(View.GONE);
                    } else {
                        try{ subsub.clear();
                            spinner_child2.setVisibility(View.GONE);
                        }catch (Exception e ){


                        }


                    }

                    // Toast.makeText(Add_category.this, "selected " + selectedItemText[0], Toast.LENGTH_SHORT).show();
                    idz = SUBSub_arrayList2.get(position - 1).getId();
                    yearsspin.setVisibility(View.GONE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return selectedItemText[0];
    }
    private String CarsSpinnerItem2(ArrayList<String> spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(this, R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    selectedItemText[0] = parent.getItemAtPosition(position).toString();
                    Log.i("SUBSub_arrayList", String.valueOf(SUBSub_arrayList2.get(0).getName()));
                    //  Toast.makeText(Add_category.this, "selected " + SUBSub_arrayList2.get(position - 1).getId() + SUBSub_arrayList2.get(position - 1).getName(), Toast.LENGTH_SHORT).show();


                    if (!SUBSub_arrayList2.get(position - 1).getSubCategoriess().isEmpty()) {
                        spinner_child2.setVisibility(View.VISIBLE);

                        subsub = new ArrayList<String>();
                        subsub.add("إختـر النوع");

                        for (int catsub = 0; catsub < SUBSub_arrayList2.get(position - 1).getSubCategoriess().size(); catsub++) {
                            String subnames = SUBSub_arrayList2.get(position - 1).getSubCategoriess().get(catsub).getName();
                            String subid = SUBSub_arrayList2.get(position - 1).getSubCategoriess().get(catsub).getId();
                            String suborder = SUBSub_arrayList2.get(position - 1).getSubCategoriess().get(catsub).getOrder();
                            subsub.add(subnames);

                        }
                        SelectedSpinnerItem3(subsub, spinner_child2, 2);
                        ArrayList<String> years = new ArrayList<String>();
                        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
                        years.add(0,"كل الاعوام");
                        for (int i = 1990; i <= thisYear; i++) {
                            years.add(Integer.toString(i));
                        }

                        SelectedSpinnerItemyear(years, yearsspin, 1);
                        yearsspin.setVisibility(View.VISIBLE);

                    } else {
                        try{ subsub.clear();
                            spinner_child2.setVisibility(View.GONE);
                        }catch (Exception e ){


                        }


                    }

                    // Toast.makeText(Add_category.this, "selected " + selectedItemText[0], Toast.LENGTH_SHORT).show();
                    idz = SUBSub_arrayList2.get(position - 1).getId();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return selectedItemText[0];
    }

    private void transcity() {

        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                    city_arrayval = new ArrayList<>();

                    city_arrayval.add("اختر المدينة");
                    try {

                        JSONArray main_obj = new JSONArray("[" + result + "]");

                        get_cityID_map = new HashMap<String, String>();

                        for (int index = 0; index < main_obj.length(); index++) {

                            JSONObject catgrey = main_obj.getJSONObject(index);
                            Iterator<String> iter = catgrey.keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    Object value = catgrey.get(key);
                                    Log.i("ss", (String) value);
                                    Log.i("key", key);
                                    city_arrayval.add((String) value);
                                    get_cityID_map.put((String) value, key);
                                } catch (JSONException e) {
                                    // Something went wrong!
                                }
                            }


                        }
                        SelectedSpinnerItem3(city_arrayval, city_spinner, 1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        removeSimpleProgressDialog();

                        Toast.makeText(Edit_Ad.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    removeSimpleProgressDialog();

                    Toast.makeText(Edit_Ad.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
                //Toast.makeText(Add_category.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(Edit_Ad.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, Edit_Ad.this, "http://bazar.net.sa/api/v1/city", false);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.read_ad:
                doFileUpload();
                //uploadFile(fileuris);
                break;
            default:
                break;
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
                    case "titles":
                        pos = arg1.getIntExtra("position",0);
                        items.remove(pos);
                        adapter.notifyDataSetChanged();
                        break;


                }
            }}
    }
}

