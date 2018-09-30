package com.bazar.bazars.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bazar.bazars.ItemClickListener;
import com.bazar.bazars.Items.Chat_item;
import com.bazar.bazars.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AG on 4/10/2017.
 */

public class Chat_Adapter extends RecyclerView.Adapter<Chat_Adapter.ViewHolder> {
    private View view;

    private ArrayList<Chat_item> chat_items;
    private Context context;
    private LayoutInflater mInflater;
    public Chat_Adapter(ArrayList<Chat_item> chat_items, Context context) {
        this.chat_items = chat_items;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return chat_items.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView chat_right,message_time;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            chat_right = (TextView)itemView.findViewById(R.id.chat_right);
            message_time = (TextView)itemView.findViewById(R.id.message_time);
           // itemView.setOnClickListener(this);
        }

        void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }

        @Override
        public void onClick(View v) {
          //  this.itemClickListener.onItemClick(v, getLayoutPosition());

        }
    }


    public String getCurrentTime(String adDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentdate = "";

        //  long different = endDate.getTime() - startDate.getTime();
        try{
            if (adDate != null) {
                Date date = format.parse(adDate);
                Date cdate = new Date(System.currentTimeMillis());

                long different = cdate.getTime() - date.getTime();


                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long daysInMilli = hoursInMilli * 24;
                long monthesInMilli = daysInMilli * 30;
                long yearInMilli = monthesInMilli * 12;

                long elapsedyears = different / yearInMilli;
                different = different % yearInMilli;

                long elapsedmonths = different / monthesInMilli;
                different = different % monthesInMilli;

                long elapsedDays = different / daysInMilli;
                different = different % daysInMilli;

                long elapsedHours = different / hoursInMilli;
                different = different % hoursInMilli;

                long elapsedMinutes = different / minutesInMilli;
                different = different % minutesInMilli;

                long elapsedSeconds = different / secondsInMilli;


                if (elapsedyears >= 1) {
                    currentdate = context.getString(R.string.before) + " " + elapsedyears + " " + context.getString(R.string.year) + " " + context.getString(R.string.and) + " " + elapsedmonths + " " + context.getString(R.string.month);
                } else if (elapsedmonths >= 1) {
                    currentdate = context.getString(R.string.before) + " " + elapsedmonths + " " + context.getString(R.string.month) + " " + context.getString(R.string.and) + " " + elapsedDays + " " + context.getString(R.string.day);
                } else if (elapsedDays >= 1) {
                    currentdate = context.getString(R.string.before) + " " + elapsedDays + " " + context.getString(R.string.day) + " " +context. getString(R.string.and) + " " + elapsedHours + " " + context.getString(R.string.hour);
                } else if (elapsedHours >= 1) {
                    currentdate = context.getString(R.string.before) + " " + elapsedHours + " " + context.getString(R.string.hour) + " " + context.getString(R.string.and) + " " + elapsedMinutes + " " + context.getString(R.string.min);
                } else {
                    currentdate = context.getString(R.string.before) + " " + elapsedMinutes + " " + context.getString(R.string.min);
                }
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch blockf
            e.printStackTrace();
        }
        return currentdate;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(chat_items.get(viewType).isMine()?R.layout.details_message_row:R.layout.chat_item_user, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.chat_right.setText(chat_items.get(position).getChat_right());
        holder.message_time.setText(getCurrentTime(chat_items.get(position).getMessage_time()));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}

