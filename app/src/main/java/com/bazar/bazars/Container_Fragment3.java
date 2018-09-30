package com.bazar.bazars;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bazar.bazars.Adapters.Containter_adapters;
import com.bazar.bazars.Adapters.Containter_adapters_big;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.City_model;
import com.bazar.bazars.Items.Container_items;
import com.bazar.bazars.Items.User_items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Container_Fragment3 extends Fragment implements View.OnClickListener,
        Containter_adapters.loadmore {
    private RecyclerView ads_listView;
    private ListView bigger_ads_listView;
    ArrayList<String> citiesNames;
    PreferenceHelper helper;
    Containter_adapters adapter;
      Button city_filter,camera_btn;
    public ArrayList<Container_items> ads_arrayList;
    public ArrayList<User_items> users_arrayList;
    public Button button;
    String cityID;
    SwipeRefreshLayout refreshLayout;
    String linkk = null;
    private RequestQueue requestQueue;
    ArrayList<City_model> cities;
    String ids;
    Containter_adapters_big bigAdapter;
    public static Container_Fragment3 newInstance(int position, String name) {
        Log.i("namesub", name);
        Container_Fragment3 fragment = new Container_Fragment3();
        Bundle args = new Bundle();
        args.putInt("key5", position);
        args.putString("type", name);

        fragment.setArguments(args);

        return fragment;
    }

    public Container_Fragment3() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCities();
        helper = new PreferenceHelper(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();


    }
    Bundle bundle;
    private boolean loading = true;
    LinearLayoutManager mLayoutManager;
    int pastVisiblesItems , visibleItemCount, totalItemCount;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    ProgressBar centerbar;
    ArrayList<String> kk = new ArrayList<String>();
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.ads_list_layout, container, false);
                requestQueue = Volley.newRequestQueue(getActivity());

         ads_listView = (RecyclerView) rootView.findViewById(R.id.ads_listViewa);
        ads_arrayList = new ArrayList<Container_items>();
        refreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
         mLayoutManager   = new LinearLayoutManager(getActivity());
        ads_listView.setLayoutManager(mLayoutManager);
        ads_listView.setHasFixedSize(true);
        centerbar = (ProgressBar)rootView.findViewById(R.id.centerprog);
        centerbar.setVisibility(View.GONE);
        Button bigger_shows_btn = (Button)rootView.findViewById(R.id.bigger_button_list);
        bigger_shows_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(helper.getShowType() == 0) {
                    bigAdapter = new Containter_adapters_big(getActivity(), ads_arrayList);
                    ads_listView.setAdapter(bigAdapter);
                    helper.putShowType(1);
                }else if(helper.getShowType() ==1){
                    adapter = new Containter_adapters(getActivity(),ads_arrayList);
                    ads_listView.setAdapter(adapter);
                    helper.putShowType(0);
                }

            }
        });

        city_filter = (Button)rootView.findViewById(R.id.button_filter_city);
        city_filter.setOnClickListener(this);
        camera_btn = (Button)rootView.findViewById(R.id.button_camera);
        camera_btn.setOnClickListener(this);
         bundle = getArguments();
//        Log.i("id", String.valueOf(bundle.getInt("key5")));
//        Log.i("id", String.valueOf(bundle.getInt("type")));

        trans(String.valueOf(bundle.getString("type")),String.valueOf(bundle.getInt("key5")),helper.getImagePreference());
        ids = String.valueOf(bundle.getInt("key5"));
        if(helper.getShowType() == 0){
             ads_listView.setAdapter(adapter);
        }else if(helper.getShowType() == 1){
             ads_listView.setAdapter(bigAdapter);
        }

         ads_listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
             @Override
               public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                  super.onScrolled(recyclerView, dx, dy);

                 if(dy > 0) //check for scroll down
                 {
                     visibleItemCount = mLayoutManager.getChildCount();
                     totalItemCount = mLayoutManager.getItemCount();
                     pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                     if (loading) {
                         if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 13) {
                             loading = false;
                             Log.v("...", "Last Item Wow !");
                             //Do pagination.. i.e. fetch new data
                             transNext(bundle.getString("type"),String.valueOf(bundle.getInt("key5")),helper.getImagePreference());
                         }
                     }
                 }
             }
           });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //trans(String.valueOf(bundle.getInt("key5")),helper.getImagePreference());
                refreshtrans(bundle.getString("type"),ids,helper.getImagePreference());
            //    centerbar.setVisibility(View.VISIBLE);
            //    ads_listView.setVisibility(View.GONE);

            }
        });

        return rootView;
    }



    private void transNext(String type,String id, String image) {

        Log.i("Link", "http://alsog.net/api/Advertises/alldata/"
                +  type + "/" + id + "/" + image + "/" + 0);
        Log.i("getCityId",image);

        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                page = page + 1 ;

                if (!result.equals("")) {
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
                             String created = catgrey.getString("modified") .replace("T"," ");
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
                             ads_arrayList.add(new Container_items(id, title, photo, content, user_id, city, created, users_arrayList));

                         }

                        if(helper.getShowType() == 1) {
                            bigAdapter = new Containter_adapters_big(getActivity(), ads_arrayList);
                            ads_listView.setAdapter(bigAdapter);

                        }
                        else if(helper.getShowType()==0){
                            adapter = new Containter_adapters(getActivity(),ads_arrayList);
                            ads_listView.setAdapter(adapter);


                        }
                        // refreshLayout.setRefreshing(false);
                        // transNext(linkk);
                    } catch (JSONException e) {
                        e.printStackTrace();
                     }
                }
            }

            @Override
            public void onError(String error) {
                //Toast.makeText                (getActivity(), "" + error, Toast.LENGTH_SHORT).show();
             //   Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
            }


        }, getActivity(), "http://alsog.net/api/Advertises/alldata/"
                +  type + "/" + id + "/" + image + "/" + helper.getCityId() + "/" + page , false);
    }



    private void trans(String type,String id, String image) {

        Log.i("Link", "http://alsog.net/api/Advertises/alldata/"
                +  type + "/" + id + "/" + image + "/" + 0);
        Log.i("getCityId",image);

        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                linkk = "nukk";
                if (!result.equals("")) {
                    ads_arrayList.clear();
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
                            String created = catgrey.getString("modified").replace("T", " ");
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
                            ads_arrayList.add(new Container_items(id, title, photo, content, user_id, city, created, users_arrayList));

                        }

                        if (helper.getShowType() == 1) {
                            bigAdapter = new Containter_adapters_big(getActivity(), ads_arrayList);
                            ads_listView.setAdapter(bigAdapter);

                        } else if (helper.getShowType() == 0) {
                            adapter = new Containter_adapters(getActivity(), ads_arrayList);
                            ads_listView.setAdapter(adapter);


                        }
                        // refreshLayout.setRefreshing(false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    transNext(bundle.getString("type"), String.valueOf(bundle.getInt("key5")), helper.getImagePreference());
                }
//                } else {
//                    Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onError(String error) {
                //Toast.makeText                (getActivity(), "" + error, Toast.LENGTH_SHORT).show();
               // Toast.makeText(getActivity(), getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
            }


        }, getActivity(), "http://alsog.net/api/Advertises/alldata/"
                +  type + "/" + id + "/" + image + "/" + helper.getCityId() +"/1" , false);
    }

    int page = 2;
    private void refreshtrans(String type,String id, String image) {

        Log.i("Link", "http://alsog.net/api/Advertises/alldata/"
                +  type + "/" + id + "/" + image + "/" + 0);
        Log.i("getCityId",image);

        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                linkk = "nukk";
                page = 2;
                ads_arrayList.clear();
                if (!result.equals("")) {
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
                             String created = catgrey.getString("modified") .replace("T"," ");
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
                             ads_arrayList.add(new Container_items(id, title, photo, content, user_id, city, created, users_arrayList));

                         }

                        if(helper.getShowType() == 1) {
                            bigAdapter = new Containter_adapters_big(getActivity(), ads_arrayList);
                            ads_listView.setAdapter(bigAdapter);

                        }
                        else if(helper.getShowType()==0){
                            adapter = new Containter_adapters(getActivity(),ads_arrayList);
                            ads_listView.setAdapter(adapter);


                        }
                         refreshLayout.setRefreshing(false);
                         transNext(bundle.getString("type"),String.valueOf(bundle.getInt("key5")),helper.getImagePreference());
                    } catch (JSONException e) {
                        e.printStackTrace();
                     }
                }
            }

            @Override
            public void onError(String error) {
                //Toast.makeText                (getActivity(), "" + error, Toast.LENGTH_SHORT).show();
               // Toast.makeText(getActivity(), getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
            }


        }, getActivity(), "http://alsog.net/api/Advertises/alldata/"
                +  type + "/" + id + "/" + image + "/" + helper.getCityId() + "/1" , false);
    }

    public ArrayList<City_model> getCities(){
        cities = new ArrayList<City_model>();
        citiesNames = new ArrayList<String>();
        new Json_Controller().GetDataFromServer(new VolleyCallback() {

            @Override
            public void onSuccess(String result) throws JSONException {
                JSONObject main_obj = new JSONObject(result);
                JSONArray ads = main_obj.getJSONArray("data");
                cities.add(new City_model("0","كل المدن"));


                    try {
                        for (int index = 0; index < ads.length(); index++) {

                            JSONObject catgrey = ads.getJSONObject(index);

                            String id = catgrey.getString("id");
                            String name = catgrey.getString("name");
                            City_model city  = new City_model("","");
                            city.setId(id);
                            city.setName(name);
                            //citiesNames.add(value);
                            cities.add(city);




                        }

                        for(int s=0;s<cities.size();s++){
                            String oo = new String();
                            oo = cities.get(s).getName();
                            citiesNames.add(oo);
                        }
                        } catch (JSONException e) {
                        // Something went wrong!

                }


                }


            @Override
            public void onError(String error) {

            }
        },getActivity(),"http://alsog.net/api/cities",false);
        return cities;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_filter_city:
                openCityChooser();
                break;

            case R.id.button_camera:
                if(helper.getImagePreference().equals("0")) {
                    helper.putImagePreference("1");
                    trans(String.valueOf(bundle.getInt("type")),String.valueOf(bundle.getInt("key5")),"1");
                }else {
                    helper.putImagePreference("0");
                    trans(String.valueOf(bundle.getInt("type")),String.valueOf(bundle.getInt("key5")),"0");

                }

                break;
            default:
                break;

        }


    }

    private void openCityChooser() {
        final Dialog mDialog = new Dialog(getActivity(), R.style.MyDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mDialog.setContentView(R.layout.city_filter);
        final Spinner citySpinner = (Spinner) mDialog.findViewById(R.id.city_spinner);
        Button btnCancel = (Button) mDialog.findViewById(R.id.cancel_choosecitybtn);

         ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_row, R.id.city_item, citiesNames);
        citySpinner.setAdapter(adapter);
        citySpinner.setSelection(helper.getCitySelection());


        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                citySpinner.setSelection(position,true);
                helper.putCitySelection(position);
                cityID = cities.get(position).getId();
                helper.putCitID(cityID);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button btnSend = (Button) mDialog.findViewById(R.id.choose_citybtn);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trans(String.valueOf(bundle.getInt("type")),ids,helper.getImagePreference());
               // transNext(linkk);
                ads_listView.scrollToPosition(1);
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

}