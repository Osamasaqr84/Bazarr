package com.bazar.bazars.Connect_TO_Server;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bazar.bazars.PreferenceHelper;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by AG on 12/29/2016.
 */

public class Json_Controller {

    PreferenceHelper helper;
    private Response.ErrorListener mErrorListener;
    public void GetDataFromServer(final VolleyCallback callback, final Activity context, String url, final boolean Authorization) {
        final String tag_string_req = "string_req";
      //  final ProgressDialog finalDialog = dialog;
      RequestQueue queue = Volley.newRequestQueue(context);
      //RequestQueue queue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()));

      //  MyApplication.getInstance().getRequestQueue().getCache().clear();

        final StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {  //finalDialog.dismiss();
                    String encodingString = URLEncoder.encode(response, "ISO-8859-1");
                    response = URLDecoder.decode(encodingString, "UTF-8");

                    } catch (UnsupportedEncodingException ex) {
                        ex.printStackTrace();
                    }
                    Log.d("fucking response", response);
                    try {
                        callback.onSuccess(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
              }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                callback.onError(error.getMessage());
              //  finalDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                if (Authorization){
                    SharedPreferences prefs = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
                    String saved_token = prefs.getString("token", null);
                    if (saved_token != null) {
                        params.put("Authorization", "Bearer " + saved_token);
                    }
                }
                return params;
            }
        };

//        Cache cache=queue.getCache();
//        Log.i("cash in volly",  queue.getCache().toString());
//        strReq.setShouldCache(false);
//        queue.getCache().clear();
//        queue.add(strReq);
//      Log.i("cash2 in volly",  queue.getCache().toString());


      MyApplication.getInstance().addToRequestQueue(strReq);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////
    public void PostData2Server(final Activity context, String url, final String requestBody, final VolleyCallback callback, final boolean Authorization,Response.ErrorListener listener) {
        final String tag_string_req = "post_json";

       // ProgressDialog dialog = null;
     //   if (dialog == null) {
       //     dialog = createProgressDialog(context);
       //     dialog.show();
   //     } else {
     //   }

      // final ProgressDialog finalDialog = dialog;
        this.mErrorListener = listener;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                 //   finalDialog.dismiss();
                    callback.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // callback.onError(error.getMessage());
                mErrorListener.onErrorResponse(error);

                //  finalDialog.dismiss();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json; charset=utf-8");
                params.put("Content-Type", "application/json; charset=utf-8");
                if (Authorization){
                    SharedPreferences prefs = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
                    String saved_token = prefs.getString("token", null);
                    if (saved_token != null) {
                        params.put("Authorization", "Bearer " + saved_token);
                    }
                }
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }


    public void PostData2Server(final Activity context, String url, final String requestBody, final VolleyCallback callback, final boolean Authorization) {
        final String tag_string_req = "post_json";

        // ProgressDialog dialog = null;
        //   if (dialog == null) {
        //     dialog = createProgressDialog(context);
        //     dialog.show();
        //     } else {
        //   }

        // final ProgressDialog finalDialog = dialog;
     //   this.mErrorListener = listener;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //   finalDialog.dismiss();
                    callback.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 callback.onError(error.getMessage());
              //  mErrorListener.onErrorResponse(error);

                //  finalDialog.dismiss();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                helper = new PreferenceHelper(context);
                params.put("Accept", "application/json; charset=utf-8");
                params.put("Content-Type", "application/json; charset=utf-8");
                if (Authorization){
                     String saved_token = helper.getAPI_TOKEN();
                    if (saved_token != null) {
                        params.put("Authorization", "Bearer " + saved_token);
                    }
                }
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }

    public void PostNotification(final Activity context, String url, final String requestBody, final VolleyCallback callback, final boolean Authorization) {
        final String tag_string_req = "post_json";
        // ProgressDialog dialog = null;
        //   if (dialog == null) {
        //     dialog = createProgressDialog(context);
        //     dialog.show();
        //     } else {
        //   }

        // final ProgressDialog finalDialog = dialog;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //   finalDialog.dismiss();
                    callback.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.getMessage());
                //  finalDialog.dismiss();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                helper = new PreferenceHelper(context);
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Accept", "application/json; charset=utf-8");
                params.put("Content-Type", "application/json; charset=utf-8");
                if (Authorization){
                     String saved_token = helper.getNotificationId();
                    if (saved_token != null) {
                        params.put("Authorization", "Basic " + saved_token);
                    }
                }
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }

    public void PutData2Server(final Activity context, String url, final String requestBody, final VolleyCallback callback, final boolean Authorization) {
        final String tag_string_req = "post_json";

        ProgressDialog dialog = null;
       // if (dialog == null) {
           // dialog = createProgressDialog(context);
          //  dialog.show();
      //  } else {
     //   }

       // final ProgressDialog finalDialog = dialog;

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                   // finalDialog.dismiss();
                    callback.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.getMessage());
                //finalDialog.dismiss();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json; charset=utf-8");
                params.put("Content-Type", "application/json; charset=utf-8");
                if (Authorization){
                    SharedPreferences prefs = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
                    String saved_token = prefs.getString("token", null);
                    if (saved_token != null) {
                        params.put("Authorization", "Bearer " + saved_token);
                    }
                }
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
       // MyApplication.getInstance().addToRequestQueue(stringRequest);
    }



}
