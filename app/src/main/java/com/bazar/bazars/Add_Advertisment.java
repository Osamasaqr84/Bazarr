package com.bazar.bazars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

/**
 * Created by AG on 3/29/2017.
 */

public class Add_Advertisment extends Activity implements View.OnClickListener {

    TextView cars_sell, lorry_sell, lorry_rent, dabab_sell,
            exswarat, bilding_rent, buildings_sell, mobiles_computer, other_goods;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_advertisment);

        cars_sell = (TextView) findViewById(R.id.cars_sell);
        lorry_sell = (TextView) findViewById(R.id.lorry_sell);
        lorry_rent = (TextView) findViewById(R.id.lorry_rent);
        dabab_sell = (TextView) findViewById(R.id.dabab_sell);
        exswarat = (TextView) findViewById(R.id.exswarat);
        bilding_rent = (TextView) findViewById(R.id.building_rent);
        buildings_sell = (TextView) findViewById(R.id.buildings_sell);
        mobiles_computer = (TextView) findViewById(R.id.mobiles_computers);
        other_goods = (TextView) findViewById(R.id.other_goods);

        cars_sell.setOnClickListener(this);
        lorry_sell.setOnClickListener(this);
        lorry_rent.setOnClickListener(this);
        dabab_sell.setOnClickListener(this);
        exswarat.setOnClickListener(this);
        bilding_rent.setOnClickListener(this);
        buildings_sell.setOnClickListener(this);
        mobiles_computer.setOnClickListener(this);
        other_goods.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cars_sell:
                Intent intent1 = new Intent(Add_Advertisment.this, Qasam.class);
                startActivity(intent1);
                break;
            case R.id.lorry_sell:
                Intent intent2 = new Intent(Add_Advertisment.this, Qasam.class);
                startActivity(intent2);
                break;
            case R.id.lorry_rent:
                Intent intent3 = new Intent(Add_Advertisment.this, Qasam.class);
                startActivity(intent3);
                break;
            case R.id.dabab_sell:
                Intent intent4 = new Intent(Add_Advertisment.this, Qasam.class);
                startActivity(intent4);
                break;
            case R.id.exswarat:
                Intent intent5 = new Intent(Add_Advertisment.this, Qasam.class);
                startActivity(intent5);
                break;
            case R.id.building_rent:
                Intent intent6 = new Intent(Add_Advertisment.this, Qasam.class);
                startActivity(intent6);
                break;
            case R.id.buildings_sell:
                Intent intent7 = new Intent(Add_Advertisment.this, Qasam.class);
                startActivity(intent7);
                break;
            case R.id.mobiles_computers:
                Intent intent8 = new Intent(Add_Advertisment.this, Qasam.class);
                startActivity(intent8);
                break;
            case R.id.other_goods:
                Intent intent9 = new Intent(Add_Advertisment.this, Qasam.class);
                startActivity(intent9);
                break;


        }
    }
}
