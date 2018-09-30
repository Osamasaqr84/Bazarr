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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.Tab_one_items;
import com.bazar.bazars.Items.Tab_three_item;
import com.bazar.bazars.Items.Tab_two_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by tournedo2003 on 3/21/17.
 */

public class Search_activity extends AppCompatActivity {


    private Typeface mediumFace, lightFace;
    private ProgressDialog dialog;
    private ArrayList<String> country_array, cities_array, specialty_array;
    private Spinner country_spinner, spinner_child2, year_spinner;
     public static ArrayList<Tab_one_items> Cat_arrayList;
    public static ArrayList<Tab_two_item> SUB_arrayList;
    public static ArrayList<Tab_three_item> SUBSub_arrayList;
    public static ArrayList<Tab_three_item> SUBSub_arrayList2;
    public static ArrayList<String> city_array;
    public static ArrayList<String> spinnerchild2;
    private String idz;
    PreferenceHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_activity);
        trans();

        ImageView image = (ImageView) findViewById(R.id.button_search);
        final EditText text = (EditText) findViewById(R.id.search_view);
        helper = new PreferenceHelper(this);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dd = text.getText().toString();

                Intent intent = new Intent(Search_activity.this, Search_results.class);
            //    intent.putExtra("link", "");
                intent.putExtra("text",dd);
                intent.putExtra("type",1);
                startActivity(intent);

            }
        });
        year_spinner = (Spinner) findViewById(R.id.spinner_model_selection);

        Button button_camera = (Button) findViewById(R.id.button_continue);
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (country_spinner.getSelectedItem() == country_spinner.getItemAtPosition(0)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.chooseCar), Toast.LENGTH_SHORT).show();
                }else if (year_spinner.getSelectedItem() == year_spinner.getItemAtPosition(0)) {
                    Intent intent = new Intent(Search_activity.this, Search_results.class);
//                    intent.putExtra("link", "http://alsog.net/api/advertises/alldata/"
//                                    +  2 + "/" + idz + "/" + 0 + "/" + helper.getCityId() + "/1");
                    intent.putExtra("cattype",3);
                    intent.putExtra("idz",idz);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Search_activity.this, Search_results.class);
//                    intent.putExtra("link", "http://alsog.net/api/advertises/alldata/"
//                            +  2 + "/" + idz + "/" + 0 + "/" + helper.getCityId() + "/1");
                    intent.putExtra("cattype",3);
                    intent.putExtra("idz",idz);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                }
            }
        });

//        ArrayList<Tab_one_items> myList = (ArrayList<Tab_one_items>) getIntent().getSerializableExtra("mylist");

//        Log.i("fucklist", String.valueOf(myList));
        country_spinner = (Spinner) findViewById(R.id.spinner_child1);


        spinner_child2 = (Spinner) findViewById(R.id.spinner_child2);
        ArrayList<String> years = new ArrayList<String>();
        years.add(0,getString(R.string.allyears));
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1990; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        SelectedSpinneryear(years, year_spinner, 1);

        spinnerchild2 = new ArrayList<String>();
        spinnerchild2.add("إختـر الموديل");
        SelectedChildSpinnerItem(spinnerchild2, spinner_child2, 1);
    }
int newpos = 0;
    private String SelectedSpinnerItem(ArrayList<String> spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("position", String.valueOf(position));
                if (position > 0) {
                    selectedItemText[0] = parent.getItemAtPosition(position).toString();
                    SUBSub_arrayList2 = new ArrayList<Tab_three_item>();

                    //  Toast.makeText(Search_activity.this, "selected " + Cat_arrayList.get(1).getSubCategories().get(position - 1).getSubCategoriess().get(0).getName(), Toast.LENGTH_SHORT).show();


                    spinnerchild2 = new ArrayList<String>();
                    spinnerchild2.add("إختـر الموديل");

                    for (int catsub = 0; catsub < Cat_arrayList.get(1).getSubCategories().get(position - 1).getSubCategoriess().size(); catsub++) {
                        String subnames = Cat_arrayList.get(1).getSubCategories().get(position - 1).getSubCategoriess().get(catsub).getName();
                        String subid = Cat_arrayList.get(1).getSubCategories().get(position - 1).getSubCategoriess().get(catsub).getId();

                        Log.i("subnames", subnames);
                        spinnerchild2.add(subnames);
                        SUBSub_arrayList2.add(new Tab_three_item(subid, subnames));

                    }
                    newpos = position;
                    SelectedSpinnerItem2(spinnerchild2, spinner_child2, 2);
                    if(spinner.getSelectedItem() != spinner.getItemAtPosition(0)){
                        idz = Cat_arrayList.get(1).getSubCategories().get(position-1).getId();

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return selectedItemText[0];
    }

    private String SelectedChildSpinnerItem(ArrayList<String> spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};
        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("position", String.valueOf(position));
                if (position > 0) {
                    selectedItemText[0] = parent.getItemAtPosition(position).toString();
                    SUBSub_arrayList2 = new ArrayList<Tab_three_item>();

                    //  Toast.makeText(Search_activity.this, "selected " + Cat_arrayList.get(1).getSubCategories().get(position - 1).getSubCategoriess().get(0).getName(), Toast.LENGTH_SHORT).show();


                    spinnerchild2 = new ArrayList<String>();
                    spinnerchild2.add("إختـر الموديل");

                    for (int catsub = 0; catsub < Cat_arrayList.get(1).getSubCategories().get(position - 1).getSubCategoriess().size(); catsub++) {
                        String subnames = Cat_arrayList.get(1).getSubCategories().get(position - 1).getSubCategoriess().get(catsub).getName();
                        String subid = Cat_arrayList.get(1).getSubCategories().get(position - 1).getSubCategoriess().get(catsub).getId();
                        String suborder = Cat_arrayList.get(1).getSubCategories().get(position - 1).getSubCategoriess().get(catsub).getOrder();

                        Log.i("subnames", subnames);
                        spinnerchild2.add(subnames);
                        SUBSub_arrayList2.add(new Tab_three_item(subid, subnames));

                    }
                    SelectedSpinnerItem2(spinnerchild2, spinner_child2, 2);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return selectedItemText[0];
    }


    private String SelectedSpinneryear(ArrayList<String> spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};

        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("position", String.valueOf(position));
                if(position == 0){
                    selectedItemText[0] ="0";
;                }else
                if (position > 0) {
                    selectedItemText[0] = parent.getItemAtPosition(position).toString();
                 }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return selectedItemText[0];
    }

    private String SelectedSpinnerItem2(ArrayList spinner_arrayList, final Spinner spinner, final int in) {

        final String[] selectedItemText = {""};

        final ArrayAdapter<String> cityArrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_item, spinner_arrayList);
        cityArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(cityArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    selectedItemText[0] = parent.getItemAtPosition(position).toString();
                    Log.i("SUBSub_arrayList", String.valueOf(SUBSub_arrayList2.get(0).getName()));
                    //  Toast.makeText(Search_activity.this, "selected " + SUBSub_arrayList2.get(position - 1).getId() + SUBSub_arrayList2.get(position - 1).getName(), Toast.LENGTH_SHORT).show();


                    //   Toast.makeText(Search_activity.this, "selected " + selectedItemText[0], Toast.LENGTH_SHORT).show();

                        idz = SUBSub_arrayList2.get(position - 1).getId();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return selectedItemText[0];
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



    private void trans() {

        showSimpleProgressDialog(this,"",getString(R.string.pleasewait),false);
        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                    Cat_arrayList = new ArrayList<Tab_one_items>();
                    city_array = new ArrayList<String>();

                    city_array.add("إختـر الماركة");
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
                                    Tab_two_item d = new Tab_two_item(subid, subname,  SUBSub_arrayList);
                                    d.setId(subid);
                                    d.setSubCategoriess(SUBSub_arrayList);
                                    d.setName(subname);
                                    SUB_arrayList.add(d);
                                }
                                for (int i = 0; i<SUB_arrayList.size() ;i++) {
                                    String ll = "";
                                    ll = SUB_arrayList.get(i).getName();
                                    city_array.add(ll);

                                }
                            }


                            Cat_arrayList.add(new Tab_one_items(catid, catname, SUB_arrayList));

                        }

                        SelectedSpinnerItem(city_array, country_spinner, 1);
                        removeSimpleProgressDialog();
                    } catch (JSONException e) {
                        removeSimpleProgressDialog();

                        e.printStackTrace();
                        Toast.makeText(Search_activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    removeSimpleProgressDialog();

                    Toast.makeText(Search_activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
                Toast.makeText(Search_activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, Search_activity.this, "http://newharag.codesroots.com/api/categories", false);


    }
}


