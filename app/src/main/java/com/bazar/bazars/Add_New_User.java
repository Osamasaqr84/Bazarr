package com.bazar.bazars;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bazar.bazars.Connect_TO_Server.ConnectivityReceiver;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AG on 4/9/2017.
 */

public class Add_New_User extends Activity implements View.OnClickListener {

    private EditText username, password, email, mobile;
    private Button register;
    private String emailPattern;
    private CheckBox terms_checkBox;
    private TextView terms_condition;
    String phone,code;
    private PreferenceHelper helper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_layout);
        Intent intent = getIntent();
        if(intent!=null){
            phone = intent.getStringExtra("phone");
            code = intent.getStringExtra("code");
        }
        username = (EditText) findViewById(R.id.username_editText);
        password = (EditText) findViewById(R.id.password_editText);
        email = (EditText) findViewById(R.id.email_editText);

        register = (Button) findViewById(R.id.register_btn);
        terms_condition = (TextView) findViewById(R.id.terms_condition_textView);
        TextView title_of_page = (TextView) findViewById(R.id.title_of_page);
        ImageView back_imageView = (ImageView) findViewById(R.id.back_imageView);
        helper = new PreferenceHelper(this);
        back_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        terms_condition.setMovementMethod(LinkMovementMethod.getInstance());

        terms_checkBox = (CheckBox) findViewById(R.id.terms_checkbox);



        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (username.getText().toString().trim().length() < 4 || username.getText().toString().trim().length() <= 0) {
                        Toast.makeText(getApplicationContext(), "من فضلك قم بادخال اسم المستخدم بقيمة اكبر من اربع حروف !", Toast.LENGTH_LONG).show();
                        username.setText("");
                    }
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (password.getText().toString().trim().length() < 7 || password.getText().toString().trim().length() <= 0) {
                        Toast.makeText(getApplicationContext(), "من فضلك قم بادخال كلمة المرور بقيمة اكبر من سبع حروف !!", Toast.LENGTH_LONG).show();
                        password.setText("");
                    }
                }
            }
        });
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
                if (terms_checkBox.isChecked()) {
                    if (password.getText().length() !=0 && username.getText().length() !=0 && email.getText().length() != 0) {

                        if (ConnectivityReceiver.isConnected()) {
                            JSONObject post_dict = new JSONObject();
                            try {
                                post_dict.put("username", username.getText().toString());
                                post_dict.put("password", password.getText().toString());
                                post_dict.put("mobile",phone );
                                post_dict.put("active",1 );
                                post_dict.put("email_verified", 1);
                                post_dict.put("email", email.getText().toString());

                                showSimpleProgressDialog(Add_New_User.this,"",getString(R.string.pleasewait),false);

                                final String requestBody = post_dict.toString();
                                new Json_Controller().PostData2Server(Add_New_User.this,"http://alsog.net/api/users/register", requestBody, new VolleyCallback() {
                                    @Override
                                    public void onSuccess(String result) {
                                        removeSimpleProgressDialog();
                                        if (!result.equals("")) {
                                            try {
                                                JSONObject object = new JSONObject(result);
                                                if(object.getBoolean("success")){
                                                    Toast.makeText(Add_New_User.this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                                                    helper.setAPI_TOKEN(object.getJSONObject("data").getString("token"));
                                                    helper.putUserID(object.getJSONObject("data").getString("id"));
                                                    helper.putUserName(username.getText().toString());
                                                    startActivity(new Intent(Add_New_User.this, Log_In.class));
                                                    finish();
                                                } else {
                                                    Toast.makeText(Add_New_User.this, getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(Add_New_User.this, "حدث خطا أثناء الاتصال بالسيرفر !!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(String error) {
                                        removeSimpleProgressDialog();
                                        Toast.makeText(Add_New_User.this, getString(R.string.error), Toast.LENGTH_SHORT).show();

                                    }
                                }, false);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            removeSimpleProgressDialog();

                            Toast.makeText(Add_New_User.this, getString(R.string.checkconnectivity), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        removeSimpleProgressDialog();
                        Toast.makeText(Add_New_User.this, getString(R.string.checkfields), Toast.LENGTH_SHORT).show();

                    }


                    break;
                }else {
                    Toast.makeText(Add_New_User.this, getString(R.string.checkagree), Toast.LENGTH_SHORT).show();

                }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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




