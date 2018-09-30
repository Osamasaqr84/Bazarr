package com.bazar.bazars.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bazar.bazars.Items.Message_Item;
import com.bazar.bazars.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AG on 4/9/2017.
 */

public class Message_Adapter extends BaseAdapter {
    private ArrayList<Message_Item> message_items;
    private Context context;
    private LayoutInflater mInflater;
    private View view;


    public Message_Adapter(ArrayList<Message_Item> message_items, Context context) {
        this.message_items = message_items;
        this.context = context;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return message_items.size();
    }

    @Override
    public Object getItem(int i) {
        return message_items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
         TextView title_message,isopen;
         TextView time_send;
         TextView sender_message;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        view = convertView;

        if (view == null) {

            view = mInflater.inflate(R.layout.message_row, parent,false);
            holder = new ViewHolder();

            holder.title_message = (TextView) view.findViewById(R.id.mess_title);
            holder.time_send = (TextView) view.findViewById(R.id.time_mess);
            holder.sender_message = (TextView) view.findViewById(R.id.sender_mess);
            holder.isopen = (TextView)view.findViewById(R.id.isopen);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.title_message.setText(message_items.get(position).getMessage_title());
        holder.time_send.setText(getCurrentTime(message_items.get(position).getTime_send()));
        holder.sender_message.setText(message_items.get(position).getSender_name());



        if (position % 2 == 1) {
            view.setBackgroundColor(Color.WHITE);
        } else {
            view.setBackgroundColor(Color.rgb(235, 235, 235)
            );
        }


        return view;
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


//    public void filter(String chartext) {
//        chartext = chartext.toLowerCase(Locale.getDefault());
//        message_items.clear();
//        if (chartext.length() == 0) {
//            message_items.addAll(message_items);
//        } else {
//            for (Message_Adapter wp : message_items) {
//                if (wp.get.toLowerCase(Locale.getDefault()).contains(chartext)) {
//                    message_items_lists.add(wp);
//                }
//
//            }
//
//        }
//        notifyDataSetChanged();
//
//    }
}
