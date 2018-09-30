package com.bazar.bazars;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ali on 4/7/2017.
 */

public class RelatedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView imageview;

    ItemClickListener itemClickListener;

    public RelatedHolder(View itemView) {

        super(itemView);

        imageview = (ImageView) itemView.findViewById(R.id.relatedimage);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }

    {
    }
}
