package com.bazar.bazars;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AG on 4/9/2017.
 */

public class Confirm_login extends Activity implements Response.ErrorListener, AsyncTaskCompleteListener {

    Button entre_regist,returnreg;
    String phone="",code="";
    TextView phoneNum;
    RequestQueue requestQueue;
    EditText confrimcode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_login);
        Intent i = getIntent();
        if(i!=null) {
            phone = i.getStringExtra("phone");
            code = i.getStringExtra("code");
        }
        confrimcode = (EditText)findViewById(R.id.conf_num);
        requestQueue = Volley.newRequestQueue(this);
        phoneNum = (TextView)findViewById(R.id.phonenum) ;
        phoneNum.setText(phone);
        entre_regist = (Button) findViewById(R.id.entre_regist);
        returnreg =(Button)findViewById(R.id.return_reg);
        returnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        entre_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();

            }
        });

    }

    public void Register(){
        if(confrimcode.getText().length()!=4){
            Toast.makeText(this,getString(R.string.confcode),Toast.LENGTH_SHORT).show();
            return;
        }
        showSimpleProgressDialog(Confirm_login.this,"",getString(R.string.pleasewait),false);

        new Json_Controller().GetDataFromServer(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                try {
                    removeSimpleProgressDialog();
                    JSONObject jsonObject = new JSONObject(result);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Intent intent = new Intent(Confirm_login.this ,Add_New_User.class);
                        if(phone!=null && code!=null) {
                            intent.putExtra("phone", phone);
                            intent.putExtra("code", code);
                        }
                        startActivity(intent);
                        finish();
                    }else {
                        removeSimpleProgressDialog();
                        Toast.makeText(Confirm_login.this,getString(R.string.unvalidcode), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                removeSimpleProgressDialog();
                Toast.makeText(Confirm_login.this,getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
            }
        },Confirm_login.this,"https://api.authy.com/protected/json/phones/verification/check?api_key=v9D7D9t0RRzUmsEzYjY5dyYeXgrNxdes&phone_number=" +
                phone + "&country_code="+ code +
                "&verification_code=" +confrimcode.getText().toString(),false);


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
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode){
            case 37:
                try {
                    removeSimpleProgressDialog();
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Intent intent = new Intent(Confirm_login.this ,Add_New_User.class);
                        if(phone!=null && code!=null) {
                            intent.putExtra("phone", phone);
                            intent.putExtra("code", code);
                        }
                        startActivity(intent);
                        finish();
                    }else {
                        removeSimpleProgressDialog();
                        Toast.makeText(Confirm_login.this,getString(R.string.unvalidcode), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
