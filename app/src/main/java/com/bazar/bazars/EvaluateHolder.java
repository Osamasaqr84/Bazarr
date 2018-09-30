package com.bazar.bazars;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ali on 4/7/2017.
 */

public class EvaluateHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView sender_name,time_send,title_comment;

    ItemClickListener itemClickListener;

    public EvaluateHolder(View itemView) {

        super(itemView);

        sender_name = (TextView) itemView.findViewById(R.id.evaluate_sender);
        time_send = (TextView)itemView.findViewById(R.id.evaluate_time);
        title_comment = (TextView)itemView.findViewById(R.id.evaluate_content) ;

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
