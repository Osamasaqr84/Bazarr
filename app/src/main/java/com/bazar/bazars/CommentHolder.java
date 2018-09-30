package com.bazar.bazars;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ali on 4/7/2017.
 */

public class CommentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView sender_name,time_send,title_comment;

    ItemClickListener itemClickListener;

    public CommentHolder(View itemView) {

        super(itemView);

        sender_name = (TextView) itemView.findViewById(R.id.commentClientName);
        time_send = (TextView)itemView.findViewById(R.id.commentDate);
        title_comment = (TextView)itemView.findViewById(R.id.commentText) ;

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
