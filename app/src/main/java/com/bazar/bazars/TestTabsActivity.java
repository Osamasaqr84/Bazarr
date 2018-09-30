package com.bazar.bazars;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.Tab_one_items;
import com.bazar.bazars.Items.Tab_three_item;
import com.bazar.bazars.Items.Tab_two_item;

import static com.bazar.bazars.MainActivity.Cat_arrayList;
import static com.bazar.bazars.MainActivity.SUBSub_arrayList;
import static com.bazar.bazars.MainActivity.SUB_arrayList;
import static com.bazar.bazars.MainActivity.removeSimpleProgressDialog;
import static com.bazar.bazars.MainActivity.showSimpleProgressDialog;

public class TestTabsActivity extends AppCompatActivity {

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tabs);
    }



    private void trans() {
        showSimpleProgressDialog(this,"",getString(R.string.pleasewait),false);

        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                    Cat_arrayList = new ArrayList<Tab_one_items>();
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
                                    Tab_two_item item = null;
                                    item.setName(dateObj.getString("name"));
                                    item.setId(dateObj.getString("id"));
                                    String subname = dateObj.getString("name");
                                    String subid = dateObj.getString("id");
                                    if (dateObj.has("SubCategories")) {
                                        JSONArray catsubsub = dateObj.getJSONArray("SubCategories");

                                        SUBSub_arrayList = new ArrayList<Tab_three_item>();

                                        for (int photosIndexs = 0; photosIndexs < catsubsub.length(); photosIndexs++) {

                                            JSONObject dateObjs = catsubsub.getJSONObject(photosIndexs);
                                            String subnames = dateObjs.getString("name");
                                            String subids = dateObjs.getString("id");
                                            SUBSub_arrayList.add(new Tab_three_item(subids, subnames));


                                        }
                                    }
                                    item.setSubCategoriess(SUBSub_arrayList);

                                    SUB_arrayList.add(item);
                                }
                            }


                            Cat_arrayList.add(new Tab_one_items(catid, catname,  SUB_arrayList));

                        }

                        removeSimpleProgressDialog();
                        tabLayout = (TabLayout) findViewById(R.id.tabs);

                        removeSimpleProgressDialog();

                        if (1 != 0) {
                        } else {
                            Toast.makeText(TestTabsActivity.this, "no Currency found!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(TestTabsActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TestTabsActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(TestTabsActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                removeSimpleProgressDialog();
                Toast.makeText(TestTabsActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }, TestTabsActivity.this, "http://bazar.net.sa/api/v1/cat/json", false);


    }



}
