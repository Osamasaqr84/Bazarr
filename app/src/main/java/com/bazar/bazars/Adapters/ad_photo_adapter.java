package com.bazar.bazars.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

import com.bazar.bazars.BroadcastHelper;
import com.bazar.bazars.Items.Ad_photo_item;
import com.bazar.bazars.R;

import static android.content.ContentValues.TAG;

/**
 * Created by AG on 3/28/2017.
 */

public class ad_photo_adapter extends RecyclerView.Adapter<ad_photo_adapter.ViewHolder> {


        private Context context;
        private ArrayList<Ad_photo_item> my_data_adv;
        private View view;
        private LayoutInflater mInflater;


        public ad_photo_adapter(Context context, ArrayList<Ad_photo_item> my_data_adv) {
            this.context = context;
            this.my_data_adv = my_data_adv;
            mInflater = LayoutInflater.from(context);

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.addphotoitem, parent, false);

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.imageView.setImageURI(my_data_adv.get(position).getImage());

            holder.exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    my_data_adv.remove(my_data_adv.get(position));
                    Intent pushIntent = new Intent("walker_status");
                    pushIntent.putExtra("walker_status", "a");
                    pushIntent.putExtra("position",position);
                    BroadcastHelper.sendInform(context, "title",pushIntent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return my_data_adv.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView imageView;
            private String mItem;
            ImageButton exit;

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                imageView = (ImageView) itemView.findViewById(R.id.photo_advertis);
                exit = (ImageButton)itemView.findViewById(R.id.exitt);
            }

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick " + getPosition() + "" + mItem);

            }
        }

}
