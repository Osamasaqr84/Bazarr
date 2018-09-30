package com.bazar.bazars.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bazar.bazars.ADetailed;
import com.bazar.bazars.Items.Related_Item;
import com.bazar.bazars.R;
import com.bazar.bazars.RelatedHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by AG on 3/28/2017.
 */

public class Related_Adapter extends RecyclerView.Adapter<RelatedHolder> {


        private Context context;
        private ArrayList<Related_Item> my_data_adv;
        private View view;
        private LayoutInflater mInflater;
        ADetailed adDetails ;

        public Related_Adapter(Context context, ArrayList<Related_Item> my_data_adv) {
            this.context = context;
            this.my_data_adv = my_data_adv;
            mInflater = LayoutInflater.from(context);

        }

        @Override
        public RelatedHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_item, parent, false);

            RelatedHolder holder = new RelatedHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(final RelatedHolder holder, final int position) {
             adDetails = new ADetailed();
            Glide.with(context).load(my_data_adv.get(position).getImage()).into(holder.imageview);
            holder.imageview.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ADetailed.class);
                    intent.putExtra("id_of_row",my_data_adv.get(position).getPost_id());
                    intent.putExtra("user_id",my_data_adv.get(position).getUserid());

                    context.startActivity(intent);
                    adDetails.finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            if(my_data_adv.size()>6){
                return 6;
            } else {
              return my_data_adv.size();
            }
        }

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView imageView;
            private String mItem;

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                imageView = (ImageView) itemView.findViewById(R.id.photo_advertis);
            }

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick " + getPosition() + "" + mItem);

            }
        }

}
