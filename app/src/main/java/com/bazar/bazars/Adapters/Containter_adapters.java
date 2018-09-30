package com.bazar.bazars.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bazar.bazars.ADetailed;
import com.bazar.bazars.ItemClickListener;
import com.bazar.bazars.Items.Container_items;
import com.bazar.bazars.Models.AlldataModel;
import com.bazar.bazars.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by tournedo2003 on 3/13/17.
 */

public class Containter_adapters extends RecyclerView.Adapter<Containter_adapters.ViewHolder> {

    private ArrayList<Container_items> ads_list;
    private Context context;
    private LayoutInflater mInflater;
    private Typeface lightFace;
    private View view ;
     private HashMap<Integer, Boolean> fav_hashMap;
    public  boolean currentAdvertisement;

    boolean[] animationStates;

    public Containter_adapters(Context context, ArrayList<Container_items> chalet_list_result) {
        this.context = context;
         this.ads_list = chalet_list_result;

    }



    @Override
    public Containter_adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_row_layout, parent, false);
        return new Containter_adapters.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Containter_adapters.ViewHolder holder, int position) {
        holder.username.setText((CharSequence) ads_list.get(position).getUser().get(0).getName());
        holder.title.setText(ads_list.get(position).getTitle());
       holder.time.setText(getCurrentTime( ads_list.get(position).getCreated_at()));
         holder.city.setText(ads_list.get(position).getCity());

//        if ((position >= getItemCount() - 1)){
//
//        }

        if ( ( position % 2 ) == 0 ) {
            holder.listitemm.setBackgroundColor(Color.WHITE);
        }else {
            holder.listitemm.setBackgroundColor(Color.TRANSPARENT);
        }

                 Glide.with(context).load("http://alsog.net/library/photos/" + ads_list.get(position).getImage_one()).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.progres.setVisibility(View.GONE);
                        holder.image.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.image.setVisibility(View.VISIBLE);
                        holder.progres.setVisibility(View.GONE);
                        return false;
                    }
                }).into(holder.image);
         holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(context, ADetailed.class);
                intent.putExtra("id_of_row", ads_list.get(pos).getId());
                intent.putExtra("user_id",ads_list.get(pos).getUser_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    public interface loadmore{

    }

    @Override
    public int getItemCount() {
        return ads_list.size();

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
    public void printDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);

    }
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image ;
        TextView title ,username ,time,city;
        ProgressBar progres;
        TextView notif_val ;
        ItemClickListener itemClickListener;
        LinearLayout listitemm;
        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView_logo);
            progres = (ProgressBar) itemView.findViewById(R.id.progressSpinner);
            title = (TextView) itemView.findViewById(R.id.textView_ad_title);
            username = (TextView) itemView.findViewById(R.id.textView_authorName);
            time = (TextView) itemView.findViewById(R.id.textView_time);
            city = (TextView)itemView.findViewById(R.id.textView_cityName);
            listitemm = (LinearLayout)itemView.findViewById(R.id.listitemm);
            itemView.setOnClickListener(this);
        }
        void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());

        }
    }
}
