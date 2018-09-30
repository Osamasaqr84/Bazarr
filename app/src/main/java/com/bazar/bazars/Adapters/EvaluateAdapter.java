package com.bazar.bazars.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bazar.bazars.EvaluateHolder;
import com.bazar.bazars.Items.Comment_Item;
import com.bazar.bazars.R;

import java.util.ArrayList;

/**
 * Created by AG on 4/11/2017.
 */

public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateHolder> {
    private ArrayList<Comment_Item> comment_items;
    private Context context;


    public EvaluateAdapter(ArrayList<Comment_Item> comment_items, Context context) {
        this.comment_items = comment_items;
        this.context = context;
    }

    @Override
    public EvaluateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluate_row, parent, false);
        return new EvaluateHolder(view);
    }

    @Override
    public void onBindViewHolder(final EvaluateHolder holder, int position) {
            holder.title_comment.setText(comment_items.get(position).getComment_title());
            holder.time_send.setText(comment_items.get(position).getTime_send());
            holder.sender_name.setText(comment_items.get(position).getSender_name());
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
