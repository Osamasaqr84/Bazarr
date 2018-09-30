package com.bazar.bazars;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class Qasam extends AppCompatActivity {

    RadioGroup tog,tog1,tog2;
    RadioButton ag,disag,ag1,disag1,ag2,disag2;
    ArrayList<String> ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qasam);
        tog = (RadioGroup)findViewById(R.id.toggle);
        tog1 = (RadioGroup)findViewById(R.id.toggle1);
        tog2 = (RadioGroup)findViewById(R.id.toggle2);
        ag = (RadioButton)findViewById(R.id.qagree);
        ag1 = (RadioButton)findViewById(R.id.qagree1);
        ag2 = (RadioButton)findViewById(R.id.qagree2);
        disag = (RadioButton)findViewById(R.id.qdisagree);
        disag1 = (RadioButton)findViewById(R.id.qdisagree1);
        disag2 = (RadioButton)findViewById(R.id.qdisagree2);
        ok = new ArrayList<String>();
        disag.setChecked(true);
        disag1.setChecked(true);
        disag2.setChecked(true);
        tog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.qagree){
                    ok.add("ss");
                }else if (checkedId == R.id.qdisagree){
                    ok.remove(0);
                }
                if(ok.size() == 3){
                    Intent intent = new Intent(Qasam.this,Add_category.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        tog1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.qagree1){
                    ok.add("ss");
                }else if (checkedId == R.id.qdisagree1){
                    ok.remove(0);
                }
                if(ok.size() == 3){
                    Intent intent = new Intent(Qasam.this,Add_category.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
        tog2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.qagree2){
                    ok.add("ss");
                }else if (checkedId == R.id.qdisagree2){
                    ok.remove(0);
                }
                if(ok.size() == 3){
                    Intent intent = new Intent(Qasam.this,Add_category.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
}
