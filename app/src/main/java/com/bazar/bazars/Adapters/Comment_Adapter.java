package com.bazar.bazars.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bazar.bazars.CommentHolder;
import com.bazar.bazars.Items.Comment_Item;
import com.bazar.bazars.MyADS;
import com.bazar.bazars.PreferenceHelper;
import com.bazar.bazars.R;

import java.util.ArrayList;

/**
 * Created by AG on 4/11/2017.
 */

public class Comment_Adapter extends RecyclerView.Adapter<CommentHolder> {
    private ArrayList<Comment_Item> comment_items;
    private Context context;
    PreferenceHelper helper;

    public Comment_Adapter(ArrayList<Comment_Item> comment_items, Context context) {
        this.comment_items = comment_items;
        this.context = context;
        helper = new PreferenceHelper(context);
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_icon, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommentHolder holder, final int position) {
        holder.title_comment.setText(comment_items.get(position).getComment_title());
        holder.time_send.setText(comment_items.get(position).getTime_send());
        if (comment_items.get(position).getUserid() == Integer.parseInt(helper.getSellerId())) {
            holder.sender_name.setText(comment_items.get(position).getSender_name() + " " +
            context.getString(R.string.seller));
        } else {
            holder.sender_name.setText(comment_items.get(position).getSender_name());
        }
        holder.sender_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyADS.class);
                intent.putExtra("userid",String.valueOf(comment_items.get(position).getUserid()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public int getItemCount() {
        return comment_items.size();
    }



}
