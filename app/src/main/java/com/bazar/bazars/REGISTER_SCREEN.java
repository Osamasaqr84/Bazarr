package com.bazar.bazars;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by tournedo2003 on 3/21/17.
 */

public class REGISTER_SCREEN extends AppCompatActivity implements Response.ErrorListener,
        AsyncTaskCompleteListener {


    String[] strings = {"السعودية (966)", "الكويت(965)",
            "البحرين(973)", "قطر (974)", "الامارات (971)", "عمان (968)"};
    ArrayList<String> codes;
    TextView textview;
    Spinner spinner;
    final int result_login = 1;
    EditText phone_num;
    private RequestQueue requestQueue;

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
         spinner = (Spinner) findViewById(R.id.spinner1);
        Button Login_btn = (Button) findViewById(R.id.button_login);
        phone_num = (EditText) findViewById(R.id.phone_num);
        spinner.setAdapter(new MyAdapter(this, R.layout.row, strings));
        requestQueue = Volley.newRequestQueue(this);
        codes = new ArrayList<String>();
         codes.add("966");
         codes.add("965");
         codes.add("973");
         codes.add("974");
         codes.add("971");
         codes.add("968");

        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!phone_num.getText().toString().equals("")) {
                    Login();
                }
            }

        });
    }

    public void Login(){
        showSimpleProgressDialog(REGISTER_SCREEN.this,"",getString(R.string.pleasewait),false);
        JSONObject jsonObject = new JSONObject();
        final String requestBody = jsonObject.toString();
        String url = "http://alsog.net/api/users/findbyMobile/"+  phone_num.getText().toString();
        new Json_Controller().PostData2Server(this, url, requestBody, new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                removeSimpleProgressDialog();
                JSONObject jsonObject1 = new JSONObject(result);
                JSONArray data = jsonObject1.getJSONArray("data");
                if(data.length() == 0){
                    Register();
                }else {
                    removeSimpleProgressDialog();
                    Toast.makeText(REGISTER_SCREEN.this,getString(R.string.phonenotvalid), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
                Toast.makeText(REGISTER_SCREEN.this,getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
            }
        },false);
    }



    public void Register(){
        showSimpleProgressDialog(REGISTER_SCREEN.this,"",getString(R.string.pleasewait),false);
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("api_key","v9D7D9t0RRzUmsEzYjY5dyYeXgrNxdes");
            jsonObject.put("via","sms");
            jsonObject.put("phone_number",phone_num.getText().toString());
            jsonObject.put("country_code",codes.get(spinner.getSelectedItemPosition()));
            jsonObject.put("locale", "ar");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();
        new Json_Controller().PostData2Server(this, "https://api.authy.com/protected/json/phones/" +
                "verification/start", requestBody, new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                removeSimpleProgressDialog();
                JSONObject jsonObject = new JSONObject(result);
                boolean success = jsonObject.getBoolean("success");
                if (success) {
                    Intent intent = new Intent(REGISTER_SCREEN.this, Confirm_login.class);
                    intent.putExtra("phone", phone_num.getText().toString());
                    intent.putExtra("code", codes.get(spinner.getSelectedItemPosition()));
                    Toast.makeText(REGISTER_SCREEN.this, getString(R.string.messadgesent), Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
            }

        }, false, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse n = error.networkResponse;
                String alkd = new String(n.data);
                try {
                    JSONObject jsonObject1 = new JSONObject(alkd);
                    Toast.makeText(REGISTER_SCREEN.this, jsonObject1.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                removeSimpleProgressDialog();
            }
        });

    }
//
//        HashMap<String, String> map = new HashMap<String, String>();
//        HashMap<String, String> map2 = new HashMap<String, String>();
//        map2.put("Accept","application/json");
//        map2.put("Authorization", "Token <token>");
//        map2.put("Content-Type", "application/json");
//        map.put(Const.URL,
//                "https://api.authy.com/protected/json/phones/" +
//                        "verification/start?api_key=AC9887b9b39cd57ec77cb9ba135a72d3b5" +
//                        "&via=sms&phone_number=" + phone_num.getText().toString() + "&country_code="+ codes.get(spinner.getSelectedItemPosition()) +
//                        "&locale=ar");
//
//        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
//                36, this, this,map2));




    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(REGISTER_SCREEN.this, getString(R.string.unvalidnum), Toast.LENGTH_SHORT).show();
        removeSimpleProgressDialog();
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
            switch (serviceCode){
                case 32:
                     if (!response.equals(Integer.toString(result_login))) {
                        Register();
                    }else {
                        removeSimpleProgressDialog();
                        Toast.makeText(REGISTER_SCREEN.this,getString(R.string.phonenotvalid), Toast.LENGTH_SHORT).show();
                    }

                    break;
                case 36:
                    removeSimpleProgressDialog();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            Intent intent = new Intent(REGISTER_SCREEN.this ,Confirm_login.class);
                            intent.putExtra("phone",phone_num.getText().toString());
                            intent.putExtra("code",codes.get(spinner.getSelectedItemPosition()));
                            Toast.makeText(REGISTER_SCREEN.this,getString(R.string.messadgesent),Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();

                        }else {
                            removeSimpleProgressDialog();
                            Toast.makeText(REGISTER_SCREEN.this,getString(R.string.unvalidnum), Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
    }

    public final class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            TextView label = (TextView) row.findViewById(R.id.company);
            label.setText(strings[position]);

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

