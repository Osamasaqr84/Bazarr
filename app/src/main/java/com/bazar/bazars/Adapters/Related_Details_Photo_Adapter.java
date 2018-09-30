package com.bazar.bazars.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.bazar.bazars.Items.Related_Details_Photo_item;
import com.bazar.bazars.R;

/**
 * Created by AG on 3/28/2017.
 */

public class Related_Details_Photo_Adapter  extends  RecyclerView.Adapter<Related_Details_Photo_Adapter.ViewHolder>{


    private Context context;
    private List<Related_Details_Photo_item> my_data;
    private View view;
    private LayoutInflater mInflater;

    public Related_Details_Photo_Adapter(Context context, List<Related_Details_Photo_item> my_data) {
        this.context = context;
        this.my_data = my_data;
        mInflater = LayoutInflater.from(context);

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_row_photos_details, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(context).load(my_data.get(position).getPhoto()).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photo1);
        }
    }
}
