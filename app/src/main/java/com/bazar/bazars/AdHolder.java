package com.bazar.bazars;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ali on 9/30/2016.
 */
public class AdHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView addphoto;

        ItemClickListener itemClickListener;

    public AdHolder(View itemView) {

        super(itemView);

        addphoto = (ImageView)itemView.findViewById(R.id.photo_advertis);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }
}
