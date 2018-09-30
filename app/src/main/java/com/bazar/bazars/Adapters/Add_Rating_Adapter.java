package com.bazar.bazars.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.bazar.bazars.Items.Add_rating_Item;
import com.bazar.bazars.R;

/**
 * Created by AG on 4/4/2017.
 */

public class Add_Rating_Adapter extends BaseAdapter {

    private ArrayList<Add_rating_Item> add_rating_items;
    private Context context;
    private LayoutInflater mInflater;

    public Add_Rating_Adapter(ArrayList<Add_rating_Item> add_rating_items, Context context) {
        this.add_rating_items = add_rating_items;
        this.context = context;
        mInflater = LayoutInflater.from(context);


    }

    private View view;

    @Override
    public int getCount() {
       return add_rating_items.size();
    }

    @Override
    public Object getItem(int i) {
        return add_rating_items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public class ViewHolder {
        public TextView comment ;
        public TextView date;
        public TextView commenter;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        view = convertView;

        if(view == null) {

            view = mInflater.inflate(R.layout.add_rateing_row, null);
            holder = new ViewHolder();

            holder.comment = (TextView) view.findViewById(R.id.comment_details);
            holder.date = (TextView) view.findViewById(R.id.time_comment);
            holder.commenter = (TextView) view.findViewById(R.id.commenter);


            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        holder.comment.setText(add_rating_items.get(position).getComment());
        holder.date.setText(add_rating_items.get(position).getDate_comment());
        holder.commenter.setText(add_rating_items.get(position).getCommenter());


        return view;
    }
}
