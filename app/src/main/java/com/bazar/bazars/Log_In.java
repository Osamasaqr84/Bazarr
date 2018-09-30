package com.bazar.bazars;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bazar.bazars.Connect_TO_Server.ConnectivityReceiver;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.User;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bazar.bazars.R.id.password;

/**
 * Created by tournedo2003 on 3/21/17.
 */

public class Log_In extends AppCompatActivity {

    EditText Email, Password;
    PreferenceHelper helper;
    User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Button forgot_password = (Button) findViewById(R.id.button_forgot_password);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://alsog.net/forgotPassword";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        helper= new PreferenceHelper(this);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(password);
        Button login_btn = (Button) findViewById(R.id.login_btn);
        Button register = (Button) findViewById(R.id.button_register);
        user = new User();
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Password.getText().toString().trim().equals("") || !Email.getText().toString().trim().equals("")) {
                    if (ConnectivityReceiver.isConnected()) {
                        JSONObject post_dict = new JSONObject();
                        try {
                            showSimpleProgressDialog(Log_In.this,"",getString(R.string.pleasewait),false);
                            post_dict.put("username", Email.getText().toString());
                            post_dict.put("password", Password.getText().toString());
                            final String requestBody = post_dict.toString();
                            new Json_Controller().PostData2Server(Log_In.this, "http://alsog.net/api/users/token", requestBody, new VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    if (!result.equals("")) {
                                        try{
                                            JSONObject jsonObject = new JSONObject(result);
                                            JSONObject data = jsonObject.getJSONObject("data");
                                            user.setId(data.getString("id"));
                                            //user.setName(data.getString("groupid"));
                                            user.setApi_token(data.getString("token"));
                                            user.setName(data.getString("name"));

                                            user.setEmail(data.getString("email"));
                                            user.setMobile(data.getString("mobile"));
                                            helper.setAPI_TOKEN(user.getApi_token());
                                            helper.putUserID(user.getId());
                                            helper.putUserName(user.getName());

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Intent intent = new Intent(Log_In.this, MainActivity.class);
                                        intent.putExtra("islogin","yes");
                                        intent.putExtra("username",user.getName());
                                        startActivity(intent);
                                        finish();
                                        removeSimpleProgressDialog();
                                        Toast.makeText(Log_In.this, "مرحبا " + Email.getText().toString(), Toast.LENGTH_SHORT).show();
                                        Log_In.this.finish();

                                    } else {
                                        removeSimpleProgressDialog();
                                        Toast.makeText(Log_In.this, "حدث خطا أثناء الاتصال بالسيرفر !!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onError(String error) {
                                    removeSimpleProgressDialog();
                                    Toast.makeText(Log_In.this, "اسم المستخدم او كلمة المرور غير صحيح !!", Toast.LENGTH_SHORT).show();
                                }
                            }, false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            removeSimpleProgressDialog();
                            Toast.makeText(Log_In.this, "حدث خطا أثناء الاتصال بالسيرفر !!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        removeSimpleProgressDialog();
                        Toast.makeText(Log_In.this, "من فضلك تاكد من تشغيل خدمة الانترنت", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    removeSimpleProgressDialog();
                    Toast.makeText(Log_In.this, "من فضلك تاكد من استكمال بياناتك !!!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log_In.this, REGISTER_SCREEN.class);
                startActivity(intent);
            }
        });


    }
    private void setUserStat(String ons){

        JSONObject object = new JSONObject();
        try{
            object.put("state",ons);
        }catch (JSONException e){
            e.printStackTrace();
        }
        final String requestBody = object.toString();
        new Json_Controller().PostData2Server(this,
                "http://alsog.net/api/users/edit/" + helper.getUserID() + ".json",
                requestBody, new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) throws JSONException {

                    }

                    @Override
                    public void onError(String error) {

                    }
                },true);
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
