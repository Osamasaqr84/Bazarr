package com.bazar.bazars;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.Cities_items;
import com.bazar.bazars.Items.City_model;
import com.bazar.bazars.Items.Tab_one_items;
import com.bazar.bazars.Items.Tab_three_item;
import com.bazar.bazars.Items.Tab_two_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by tournedo2003 on 4/6/17.
 */

public class
 Add_category extends AppCompatActivity {
    private Typeface mediumFace, lightFace;
    private ProgressDialog dialog;
    private ArrayList<String> country_array, cities_array, specialty_array;
    private Spinner cat_main, city_spinner, childd1_spinner, spinner_child2, yearsspin;
    private HashMap<String, String> get_cityID_map, main_cat, sub_cat, subsub_cat, year_cat;
    public static ArrayList<Tab_one_items> Cat_arrayList;
    public static ArrayList<Tab_two_item> SUB_arrayList;
    public static ArrayList<Tab_three_item> SUBSub_arrayList;
    public static ArrayList<Tab_two_item> SUBSub_arrayList2;
    public static ArrayList<Cities_items> city_array;
    public static ArrayList<String> city_arrayval;
    public static ArrayList<String> main_cat_arr;
    public static ArrayList<String> subsub;
    public static ArrayList<String> spinnerchild2;
    private String idz;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    Button button_camera;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_cat_layout);
        trans();
        transcity();
        final EditText text = (EditText) findViewById(R.id.search_view);
        //specialist_spinner = (Spinner) findViewById(R.id.spinner_model_selection);

         button_camera = (Button) findViewById(R.id.button_continue);
        button_camera.setClickable(false);
        subsub = new ArrayList<String>();
//        ArrayList<Tab_one_items> myList = (ArrayList<Tab_one_items>) getIntent().getSerializableExtra("mylist");

//        Log.i("fucklist", String.valueOf(myList));
        childd1_spinner = (Spinner) findViewById(R.id.spinner_child1);
        city_spinner = (Spinner) findViewById(R.id.city_spiiner);
        cat_main = (Spinner) findViewById(R.id.cat_main);
        yearsspin = (Spinner) findViewById(R.id.spinner_model_selection);
        showSimpleProgressDialog(this,"",getString(R.string.pleasewait),false);

        spinner_child2 = (Spinner) findViewById(R.id.spinner_child2);


        spinnerchild2 = new ArrayList<String>();
        spinnerchild2.add("إختـر القسم الفرعي");
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(city_spinner.getSelectedItem() == city_spinner.getItemAtPosition(0)){
                    Toast.makeText(Add_category.this,getString(R.string.choosecity), Toast.LENGTH_SHORT).show();
                    return;
                }else
                 if(cat_main.getSelectedItem()==cat_main.getItemAtPosition(0)) {

                    Toast.makeText(Add_category.this, getString(R.string.choosecat), Toast.LENGTH_SHORT).show();
                    return;
                }

//                if(spinner_child2.isShown()){
//                    if(spinner_child2.getSelectedItem() == spinner_child2.getItemAtPosition(0)){
//                        Toast.makeText(Add_category.this,getString(R.string.choosetype),Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }

                Intent intent = new Intent(Add_category.this, UP_LOAD.class);

                if(!spinner_child2.isShown()){
                    intent.putExtra("city_id",  get_cityID_map.get(city_spinner.getSelectedItem().toString()));
                    if( childd1_spinner != null){


                    if(childd1_spinner.getVisibility() == View.VISIBLE) {
                        if (childd1_spinner.getItemAtPosition(0) != null)
                            if (childd1_spinner.getSelectedItem() != childd1_spinner.getItemAtPosition(0)) {
                                intent.putExtra("sub_id", sub_cat.get(childd1_spinner.getSelectedItem().toString()));
                            } else {
                                intent.putExtra("sub_id", "0");
                            }
                    }else {
                        intent.putExtra("sub_id", "0");
                    }
                    }else {
                        intent.putExtra("sub_id", "0");
                    }
                    intent.putExtra("cat_id", main_cat.get(cat_main.getSelectedItem().toString()));
                    if(yearsspin.getSelectedItemPosition() == 0){
                        intent.putExtra("year",0);
                    }else if(yearsspin.getSelectedItem() == null) {
                        intent.putExtra("year",0);
                    }else {
                        intent.putExtra("year", yearsspin.getSelectedItem().toString());
                    }
                    startActivity(intent);
                    finish();

                }else{

                    intent.putExtra("city_id",  get_cityID_map.get(city_spinner.getSelectedItem().toString()));
                    intent.putExtra("sub_id", sub_cat.get(childd1_spinner.getSelectedItem().toString()));
                    if(spinner_child2!=null)
                    if(subsub_cat.get(spinner_child2.getSelectedItem().toString())!=null) {
                        intent.putExtra("subsub_id", subsub_cat.get(spinner_child2.getSelectedItem().toString()));
                    }
                    intent.putExtra("cat_id", main_cat.get(cat_main.getSelectedItem().toString()));
                    if(yearsspin.getSelectedItemPosition() == 0){
                        intent.putExtra("year",0);
                    }else if(yearsspin.getSelectedItem() == null) {
                        intent.putExtra("year",0);

                    }else {
                        intent.putExtra("year", yearsspin.getSelectedItem().toString());
                        startActivity(intent);
                    }
                    startActivity(intent);
                    finish();
                }

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
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


    private String SelectedSpinnerItemyear(ArrayList spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelected(true);
                spinner.setSelection(position);
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
        final String[] ll = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelected(true);
                spinner.setSelection(position);
                selectedItemText[0] = parent.getItemAtPosition(position).toString();
                if (cat_main.getSelectedItem().equals("غير مصنف")) {
                    spinner_child2.setVisibility(View.GONE);
                }
                if (!selectedItemText[0].equals("اثاث")) {
                    if(!selectedItemText[0].equals("غير مصنف")) {
                        childd1_spinner.setVisibility(View.VISIBLE);

                        //spinner_child2.setVisibility(View.VISIBLE);
                    }else {
                        childd1_spinner.setVisibility(View.GONE);
                        spinner_child2.setVisibility(View.GONE);
                        yearsspin.setVisibility(View.GONE);
                    }
                }  else {
                    childd1_spinner.setVisibility(View.GONE);
                    spinner_child2.setVisibility(View.GONE);
                    yearsspin.setVisibility(View.GONE);
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
                    CarsSpinnerItem2(spinnerchild2, childd1_spinner, 2);
                }else
                    if (position > 0) {
                        if (selectedItemText[0].equals("اثاث")) {
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
                                SUBSub_arrayList2.add(new Tab_two_item(subid, subnames, subsub));
                            }
                            SelectedSpinnerItem2(spinnerchild2, childd1_spinner, 2);
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
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelected(true);
                spinner.setSelection(position);
                selectedItemText[0] = parent.getItemAtPosition(position).toString();

                 if (position > 0) {
                     if(selectedItemText[0].equals("سيارات") || selectedItemText[0].equals("أجهزة")){
                             childd1_spinner.setVisibility(View.VISIBLE);
                             SUBSub_arrayList2 = new ArrayList<Tab_two_item>();
                             spinnerchild2 = new ArrayList<String>();
                             spinnerchild2.add("إختـر القسم الفرعى");
                             spinner_child2.setVisibility(View.VISIBLE);

                     } else {
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


    private String SelectedSpinnerItem3(ArrayList<String> spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelected(true);
                spinner.setSelection(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return selectedItemText[0];
    }

    private String SelectedSpinnerItem2(ArrayList<String> spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelected(true);
                spinner.setSelection(position);
                if (position > 0) {
                    selectedItemText[0] = parent.getItemAtPosition(position).toString();
                    Log.i("SUBSub_arrayList", String.valueOf(SUBSub_arrayList2.get(0).getName()));
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
                        yearsspin.setVisibility(View.GONE);
                    } else {
                        try{ subsub.clear();
                            spinner_child2.setVisibility(View.GONE);
                        }catch (Exception e ){


                        }


                    }

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
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelected(true);
                spinner.setSelection(position);
                if (position > 0) {
                    selectedItemText[0] = parent.getItemAtPosition(position).toString();
                    Log.i("SUBSub_arrayList", String.valueOf(SUBSub_arrayList2.get(0).getName()));
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


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

                    try {

                        JSONObject main_obj = new JSONObject(result);
                        JSONArray prices = main_obj.getJSONArray("data");

                        Cat_arrayList.add(0, new Tab_one_items("0", "اختر القسم",  SUB_arrayList));
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
                                    sub_cat.put(subname, subid);

                                    if (dateObj.has("sub_mini_categories")) {
                                        JSONArray catsubsub = dateObj.getJSONArray("sub_mini_categories");

                                        SUBSub_arrayList = new ArrayList<Tab_three_item>();

                                        for (int photosIndexs = 0; photosIndexs < catsubsub.length(); photosIndexs++) {

                                            JSONObject dateObjs = catsubsub.getJSONObject(photosIndexs);
                                            String subnames = dateObjs.getString("name");
                                            String subids = dateObjs.getString("id");
                                            SUBSub_arrayList.add(new Tab_three_item(subids, subnames));
                                            subsub_cat.put(subnames, subids);


                                        }
                                    }

                                    SUB_arrayList.add(new Tab_two_item(subid, subname,  SUBSub_arrayList));
                                }
                            }

                           // main_cat_arr.add(catname);
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

                        SelectedSpinnerItem(main_cat_arr, cat_main, 1);
                        button_camera.setClickable(true);
                        removeSimpleProgressDialog();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        removeSimpleProgressDialog();
                        Toast.makeText(Add_category.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    removeSimpleProgressDialog();
                    Toast.makeText(Add_category.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
               // Toast.makeText(Add_category.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(Add_category.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, Add_category.this, "http://alsog.net/api/categories", false);


    }




    private void transNew() {


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
                           // main_cat_arr.add(catname);
                            main_cat.put(catname, catid);
                            Cat_arrayList.add(new Tab_one_items(catid, catname,  SUB_arrayList));

                        }

                        CountrySpinnerItem(city_array, childd1_spinner, 1);

                        SelectedSpinnerItem(main_cat_arr, cat_main, 1);
                        button_camera.setClickable(true);
                        removeSimpleProgressDialog();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        removeSimpleProgressDialog();
                        Toast.makeText(Add_category.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    removeSimpleProgressDialog();
                    Toast.makeText(Add_category.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
                // Toast.makeText(Add_category.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(Add_category.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, Add_category.this, "http://alsog.net/api/categories", false);
    }

    private void transcity() {


        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {


                if (!result.equals("")) {
                    city_arrayval = new ArrayList<>();

                    city_arrayval.add("اختر المدينة");
                    try {
                        JSONObject main_obj = new JSONObject(result);
                        JSONArray ads = main_obj.getJSONArray("data");

                        get_cityID_map = new HashMap<String, String>();

                        for (int index = 0; index < ads.length(); index++) {

                            JSONObject catgrey = ads.getJSONObject(index);

                            String id = catgrey.getString("id");
                            String name = catgrey.getString("name");
                            City_model city  = new City_model("","");
                            city.setId(id);
                            city.setName(name);

                            city_arrayval.add((String) name);
                            get_cityID_map.put((String) name, id);
                            Iterator<String> iter = catgrey.keys();



                        }
                        SelectedSpinnerItem3(city_arrayval, city_spinner, 1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        removeSimpleProgressDialog();

                        Toast.makeText(Add_category.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    removeSimpleProgressDialog();

                    Toast.makeText(Add_category.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
                //Toast.makeText(Add_category.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(Add_category.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, Add_category.this, "http://alsog.net/api/cities", false);


    }


}
