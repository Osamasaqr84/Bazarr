package com.bazar.bazars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

/**
 * Created by AG on 3/29/2017.
 */

public class Condition_Upload extends Activity {

    String editString = "";

    Button btn;
    EditText ed;
    ToggleButton toggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.condotion_page_upload);


        final Button test1Button = (Button) findViewById(R.id.test1_button);
        final Button test2Button = (Button) findViewById(R.id.test2_button);

        final Button test1Button1 = (Button) findViewById(R.id.test1_button1);
        final Button test2Button1 = (Button) findViewById(R.id.test2_button1);


        final Button test1Button2 = (Button) findViewById(R.id.test1_button2);
        final Button test2Button2 = (Button) findViewById(R.id.test2_button2);


        View.OnClickListener topButtonsListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.test1_button) {
                    test1Button.setSelected(true);
                    test2Button.setSelected(false);
                } else {
                    test1Button.setSelected(false);
                    test2Button.setSelected(true);
                    Intent intent = new Intent(Condition_Upload.this , Add_category.class);
                    startActivity(intent);
                }
            }
        };

        View.OnClickListener topButtonsListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.test1_button1) {
                    test1Button1.setSelected(true);
                    test2Button1.setSelected(false);
                } else {
                    test1Button1.setSelected(false);
                    test2Button1.setSelected(true);
                }
            }
        };

        View.OnClickListener topButtonsListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.test1_button2) {
                    test1Button2.setSelected(true);
                    test2Button2.setSelected(false);

                } else {
                    test1Button2.setSelected(false);
                    test2Button2.setSelected(true);
                }
            }
        };


        test1Button.setOnClickListener(topButtonsListener);
        test2Button.setOnClickListener(topButtonsListener);
        test1Button.performClick();


        test1Button1.setOnClickListener(topButtonsListener1);
        test2Button1.setOnClickListener(topButtonsListener1);
        test1Button1.performClick();


        test1Button2.setOnClickListener(topButtonsListener2);
        test2Button2.setOnClickListener(topButtonsListener2);
        test1Button2.performClick();





    }
}

