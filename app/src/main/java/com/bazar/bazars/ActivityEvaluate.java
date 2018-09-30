package com.bazar.bazars;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bazar.bazars.Adapters.EvaluateAdapter;
import com.bazar.bazars.Items.Comment_Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ActivityEvaluate extends AppCompatActivity implements Response.ErrorListener, AsyncTaskCompleteListener {
        RecyclerView evaluates;
    RequestQueue requestQueue;
    String userid,adid;
    ArrayList<Comment_Item> commentitems;
    TextView addEvaluate;
    PreferenceHelper helper;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        requestQueue = Volley.newRequestQueue(this);
        addEvaluate = (TextView)findViewById(R.id.addev);
        commentitems = new ArrayList<Comment_Item>();
        helper=new PreferenceHelper(this);
        back = (ImageButton)findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEvaluateDialog();
            }
        });
        if(getIntent()!= null){
           userid = getIntent().getStringExtra("userid");
        if(getIntent().getStringExtra("adid")!= null){
            adid = getIntent().getStringExtra("adid");
        }
        }
        evaluates = (RecyclerView) findViewById(R.id.evaluate_recycler);
        evaluates.setLayoutManager(new LinearLayoutManager(this));
        evaluates.setItemAnimator(new DefaultItemAnimator());
        getAllEvaluates();
    }

    private void getAllEvaluates() {
        showSimpleProgressDialog(this,"",getString(R.string.pleasewait), false);
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Accept","application/json");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL,
                "http://alsog.net/api/rates/sellerRates/" + userid);
        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                Const.ServiceCode.getAllEvaluates, this, this,header));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        removeSimpleProgressDialog();
        Toast.makeText(this,String.valueOf(error), Toast.LENGTH_SHORT).show();
    }



    private void openEvaluateDialog() {
        final Dialog dialog = new Dialog(this,R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.evaluate);
        final RadioButton yes_btn = (RadioButton) dialog.findViewById(R.id.yes_btn);
        final RadioButton no_btn = (RadioButton) dialog.findViewById(R.id.no_btn);
        final ImageButton close = (ImageButton)dialog.findViewById(R.id.closedialog);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final EditText evaluate_text = (EditText)dialog.findViewById(R.id.evaluate_text);
        ImageButton send = (ImageButton)dialog.findViewById(R.id.sendreportz) ;

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (no_btn.isChecked()) {
                    dialog.dismiss();
                    sendEvaluate(getString(R.string.nobuy) + " " +evaluate_text.getText().toString());

                } else if (yes_btn.isChecked()) {
                    dialog.dismiss();
                    sendEvaluate(getString(R.string.yesbuy) + " " +evaluate_text.getText().toString());

                }
            }

        });
        dialog.show();
    }

    private void sendEvaluate(String message) {
        showSimpleProgressDialog(this,"",getString(R.string.pleasewait), false);
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Accept", "application/json");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL,
                "http://alsog.net/api/rates/add.json");
        map.put("user_id", userid);
        map.put("advertise_id", adid);
        map.put("stars", message);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                Const.ServiceCode.SEND_EVALUATE, this, this, header));
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


    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode){
            case Const.ServiceCode.getAllEvaluates:
                commentitems.clear();
                removeSimpleProgressDialog();
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i =0;i<jsonArray.length();i++){
                        Comment_Item commentItem = new Comment_Item();
                        JSONObject object = jsonArray.getJSONObject(i);
                        commentItem.setComment_title(object.getString("stars"));
                        commentItem.setSender_name(object.getJSONObject("user").getString("username"));
                        commentItem.setTime_send(getCurrentTime(object.getString("modified").replace("T"," ")));

                        commentitems.add(commentItem);

                    }
                    EvaluateAdapter adapter = new EvaluateAdapter(commentitems,this);
                    evaluates.setVisibility(View.VISIBLE);
                    evaluates.setAdapter(adapter);
                } catch (JSONException e) {
                    removeSimpleProgressDialog();
                    e.printStackTrace();
                }
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
        }
    }


}
