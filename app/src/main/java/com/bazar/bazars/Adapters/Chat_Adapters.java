package com.bazar.bazars.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bazar.bazars.Items.Chat_item;
import com.bazar.bazars.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AG on 4/10/2017.
 */

public class Chat_Adapters implements ListAdapter {
    private View view;

    private ArrayList<Chat_item> chat_items;
    private Context context;
    private LayoutInflater mInflater;
     public Chat_Adapters(ArrayList<Chat_item> chat_items, Context context) {
        this.chat_items = chat_items;
        this.context = context;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return chat_items.size();
    }

    @Override
    public Object getItem(int i) {
        return chat_items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public class ViewHolder {
        public TextView chat_right,message_time;

    }
      @Override
      public View getView(final int position, View convertView, final ViewGroup parent) {
            final ViewHolder holder;
            view = convertView;

            if (view == null) {
                view = mInflater.inflate(chat_items.get(position).isMine()?R.layout.details_message_row:R.layout.chat_item_user, parent,false);
                holder = new ViewHolder();
                view.setTag(holder);
                holder.chat_right = (TextView) view.findViewById(R.id.chat_right);
                holder.message_time = (TextView)view.findViewById(R.id.message_time);
                holder.chat_right.setText(chat_items.get(position).getChat_right());
                holder.message_time.setText(getCurrentTime(chat_items.get(position).getMessage_time()));
                // if(!chat_items.get(position).isMine()) {
                // holder.chat_right.setBackgroundResource(chat_background_user);
                // LinearLayout.LayoutParams para=new LinearLayout.LayoutParams(
                //         LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
                // para.bottomMargin = 10;
                // para.topMargin = 10;
                // para.leftMargin = 10;
                // para.rightMargin = 10;
                // para.gravity = Gravity.LEFT;
                // holder.chat_right.setLayoutParams(para);
                // }
            } else {
                holder = (ViewHolder) view.getTag();
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
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {

            return chat_items.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

}

