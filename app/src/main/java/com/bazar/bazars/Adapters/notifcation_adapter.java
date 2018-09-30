package com.bazar.bazars.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bazar.bazars.BroadcastHelper;
import com.bazar.bazars.Items.notification_items;
import com.bazar.bazars.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tournedo2003 on 3/31/17.
 */

public class notifcation_adapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<notification_items> not_array;

    public notifcation_adapter(Context context, ArrayList<notification_items> array){

    this.context = context;
    this.not_array = array;
        mInflater = LayoutInflater.from(context);
        notifyDataSetChanged();

    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
    @Override
    public int getCount() {
        return not_array.size();
    }

    @Override
    public Object getItem(int position) {
        return not_array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.notifcation_row_layout, null);
            holder = new ViewHolder();
            holder.viewa = (LinearLayout)convertView.findViewById(R.id.allnot);
            holder.data = (TextView) convertView.findViewById(R.id.content);
            holder.readed = (TextView)convertView.findViewById(R.id.readed);
            holder.imageView_logo = (ImageView) convertView.findViewById(R.id.imageView_logo);
            holder.created = (TextView) convertView.findViewById(R.id.textView_authorName);
            holder.viewa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent("Ada");
                    in.putExtra("pos",position);
                    BroadcastHelper.sendInform(context,"openno",in);
                    not_array.get(position).setRead_at("1");

                }
            });
            convertView.setTag(holder);

        } else {
            holder = (notifcation_adapter.ViewHolder) convertView.getTag();
        }

        Log.i("not_array.get",not_array.get(position).getData());
        holder.data.setText(not_array.get(position).getData());
        if(!not_array.get(position).getRead_at().equals("1")){
            holder.readed.setVisibility(View.VISIBLE);
            holder.imageView_logo.setVisibility(View.GONE);
        }else {
            holder.readed.setVisibility(View.INVISIBLE);
            holder.imageView_logo.setVisibility(View.VISIBLE);
        }
        holder.created.setText(getCurrentTime(not_array.get(position).getCreated_at()));
return  convertView;
    }

    static class ViewHolder{
        ImageView imageView_logo ;
        TextView data ,readed ,time , created;
        ProgressBar progres;
        TextView notif_val ;
        View viewa;
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
}
