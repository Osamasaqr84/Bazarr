package com.bazar.bazars;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.bazar.bazars.Adapters.Add_Rating_Adapter;
import com.bazar.bazars.Connect_TO_Server.Json_Controller;
import com.bazar.bazars.Connect_TO_Server.VolleyCallback;
import com.bazar.bazars.Items.Add_rating_Item;

/**
 * Created by AG on 4/4/2017.
 */

public class Add_rating extends Activity {

    private ListView add_rating_list;
    private ArrayList<Add_rating_Item> arrayList;
    private Add_Rating_Adapter adapter;
    private TextView comment, commenter, date;
//    final Context context = this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_rating);

        add_rating_list = (ListView) findViewById(R.id.add_rating_list);


        comment = (TextView) findViewById(R.id.comment_details);
        commenter = (TextView) findViewById(R.id.commenter);
        date = (TextView) findViewById(R.id.time_comment);

        final Button add_my_rating = (Button) findViewById(R.id.add_my_rating);
        add_my_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(Add_rating.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.add_my_rating);

                dialog.show();
            }
        });


        arrayList = new ArrayList<Add_rating_Item>();
        adapter = new Add_Rating_Adapter(arrayList, this);
        add_rating_list.setAdapter(adapter);


        new Json_Controller().GetDataFromServer(new VolleyCallback() {


            @Override
            public void onSuccess(String result) {
                try {
                    if (result != null) {

                        JSONObject main_obj = new JSONObject(result);
                        JSONArray news_array = main_obj.getJSONArray("data");
                        for (int i = 0; i < news_array.length(); i++) {
                            JSONObject obj = news_array.getJSONObject(i);
                            String comment_json = obj.getString("text");
                            String date_json = obj.getString("created_at");
                            JSONObject object = obj.getJSONObject("buyer");
                            String commenter_json = object.getString("name");


                            arrayList.add(new Add_rating_Item(commenter_json, date_json, comment_json));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Add_rating.this, "برجاء اعادة التحميل", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(Add_rating.this, "برجاء اعادة التحميل", Toast.LENGTH_SHORT).show();

            }
        }, Add_rating.this, "http://bazar.net.sa/api/v1/rating/135", false);


    }
}
