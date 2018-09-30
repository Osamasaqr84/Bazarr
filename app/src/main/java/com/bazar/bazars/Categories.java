package com.bazar.bazars;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.Cities_items;
import com.bazar.bazars.Items.Tab_one_items;
import com.bazar.bazars.Items.Tab_three_item;
import com.bazar.bazars.Items.Tab_two_item;

public class Categories extends AppCompatActivity {
    private Spinner cat_main, city_spinner, childd1_spinner, spinner_child2, yearsspin;
    private HashMap<String, String> get_cityID_map, main_cat, sub_cat, subsub_cat, year_cat;
    public static ArrayList<Tab_one_items> Cat_arrayList;
    public static ArrayList<Tab_two_item> SUB_arrayList;
    public static ArrayList<Tab_three_item> SUBSub_arrayList;
    public static ArrayList<Tab_two_item> SUBSub_arrayList2;
    public static ArrayList<Cities_items> city_array;
    public static ArrayList<String> city_arrayval;
    public static ArrayList<String> main_cat_arr;
    public static ArrayList<String> subsubcategory;
    public static ArrayList<String> subcategory;
    private Typeface lightFace;
    Button button_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        button_camera = (Button) findViewById(R.id.button_continue);
        button_camera.setClickable(false);
        cat_main = (Spinner)findViewById(R.id.cat_main);
        city_spinner = (Spinner)findViewById(R.id.city_spiiner);
        childd1_spinner = (Spinner)findViewById(R.id.spinner_child1);
        spinner_child2 = (Spinner)findViewById(R.id.spinner_child2);
        yearsspin = (Spinner)findViewById(R.id.spinner_model_selection);
        subcategory = new ArrayList<String>();
        subcategory.add("إختـر القسم الفرعي");
        trans();
        transcity();
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
                        SelectedSpinnerSimple(city_arrayval, city_spinner);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        removeSimpleProgressDialog();

                        Toast.makeText(Categories.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    removeSimpleProgressDialog();

                    Toast.makeText(Categories.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
                //Toast.makeText(Add_category.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(Categories.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, Categories.this, "http://bazar.net.sa/api/v1/city", false);


    }

    private String SelectedSpinnerSimple(ArrayList<String> spinner_arrayList, Spinner spinner) {
        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_item, spinner_arrayList);
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
                             main_cat.put(catname, catid);
                            Tab_one_items oneItems = new Tab_one_items(catid,catname,SUB_arrayList);
                            oneItems.setId(catid);
                            oneItems.setName(catname);
                            oneItems.setSubCategories(SUB_arrayList);

                            Cat_arrayList.add(oneItems);

                        }
                        for (int i =0;i<Cat_arrayList.size();i++){
                            String ll = Cat_arrayList.get(i).getName();
                            main_cat_arr.add(ll);
                        }
                        CountrySpinnerItem(city_array, childd1_spinner, 1);

                        MainSelectedSpinnerItem(main_cat_arr, cat_main, 1);
                        button_camera.setClickable(true);
                        removeSimpleProgressDialog();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        removeSimpleProgressDialog();
                        Toast.makeText(Categories.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    removeSimpleProgressDialog();
                    Toast.makeText(Categories.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
                // Toast.makeText(Add_category.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(Categories.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, Categories.this, "http://bazar.net.sa/api/v1/cat/json", false);


    }

    private String MainSelectedSpinnerItem(ArrayList<String> spinner_arrayList, Spinner spinner, int i) {
        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                        childd1_spinner.setVisibility(View.GONE);
                        yearsspin.setVisibility(View.GONE);
                        spinner_child2.setVisibility(View.GONE);
                }else {
                    if (Cat_arrayList.get(position-1).getCategories() != null &&
                            Cat_arrayList.get(position).getCategories().length() >= 1) {

                        for (int i = 0; i < Cat_arrayList.size(); i++) {
                            subcategory.add(Cat_arrayList.get(i).getCategories());
                        }
                        childd1_spinner.setVisibility(View.VISIBLE);
                        SelectedSpinnerSimple(subcategory, childd1_spinner);
                    } else {
                        childd1_spinner.setVisibility(View.GONE);
                        yearsspin.setVisibility(View.GONE);
                        spinner_child2.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return selectedItemText[0];
    }

    private void CountrySpinnerItem(ArrayList<Cities_items> city_array, Spinner childd1_spinner, int i) {
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
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.spinner_item, parent, false);
            TextView label = (TextView) row.findViewById(R.id.spinner_textView);
            label.setText(arrayList.get(position));

            Log.i("ss", arrayList.get(position));
            label.setTypeface(lightFace);

            return row;
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




}
