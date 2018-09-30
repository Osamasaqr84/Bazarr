package com.bazar.bazars.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bazar.bazars.ADetailed;
import com.bazar.bazars.ItemClickListener;
import com.bazar.bazars.Items.Container_items;
import com.bazar.bazars.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tournedo2003 on 3/28/17.
 */

public class myads_adapter extends RecyclerView.Adapter<myads_adapter.ViewHolder> {

    private ArrayList<Container_items> ads_list;
    private Context context;

    public myads_adapter(Context context, ArrayList<Container_items> ads_list){
        this.ads_list=ads_list;
        this.context=context;
    }

    @Override
    public myads_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_row_layout, parent, false);
        return new myads_adapter.ViewHolder(view);
     }

    @Override
    public void onBindViewHolder(final myads_adapter.ViewHolder holder, final int position) {

        try {
            holder.username.setText((CharSequence) ads_list.get(position).getUser().get(0).getName());
            holder.title.setText(ads_list.get(position).getTitle());
            holder.time.setText(getTime(ads_list.get(position).getCreated_at()));
            holder.city.setText(ads_list.get(position).getCity());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ADetailed.class);
                    intent.putExtra("id_of_row",ads_list.get(position).getId());
                    context.startActivity(intent);
                }
            });
            if (!ads_list.get(position).getImage_one().isEmpty()) {
                Picasso.with(context).load("http://alsog.net/library/photos/"+ ads_list.get(position)
                        .getImage_one()).fit().centerCrop().into(holder.image, new Callback() {

                    @Override
                    public void onSuccess() {
                        holder.image.setVisibility(View.VISIBLE);
                        holder.progres.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        holder.progres.setVisibility(View.INVISIBLE);
                        holder.image.setVisibility(View.INVISIBLE);
                    }
                });


            }


        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
        public String getTime(String adTime){

            String myStrDate = adTime;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String finaltime= "";
            int days,hours,min;
            try {
                if(myStrDate!=null) {
                    Date date = format.parse(myStrDate);
                    Date cdate = new Date(System.currentTimeMillis());

                    long difference = cdate.getTime() - date.getTime();
                    days = (int) (difference / (1000 * 60 * 60 * 24));
                    hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                    min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
                    if (days > 0) {
                       finaltime = (context.getString(R.string.before) + " " + days + " " + context.getString(R.string.day) + " " + context.getString(R.string.and) + " " + hours + " " + context.getString(R.string.hour));
                    } else if (hours > 0) {
                        finaltime=(context.getString(R.string.before) + " " + hours + " " + context.getString(R.string.hour) + " " + context.getString(R.string.and) + " " + min + " " + context.getString(R.string.min));
                    } else {
                        finaltime=(context.getString(R.string.before) + " " + min + " " + context.getString(R.string.min));

                    }
                }
                //Toast.makeText(activity,String.valueOf("days" +days+ "hours"+ hours +"min" + min),Toast.LENGTH_SHORT).show();

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return finaltime;
        }

    @Override
    public int getItemCount() {
        return ads_list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image ;
        TextView title ,username ,time,city;
        ProgressBar progres;
        View view;
        TextView notif_val ;
        ItemClickListener itemClickListener;


        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            image = (ImageView) itemView.findViewById(R.id.imageView_logo);
            progres = (ProgressBar) itemView.findViewById(R.id.progressSpinner);
            title = (TextView) itemView.findViewById(R.id.textView_ad_title);
            username = (TextView) itemView.findViewById(R.id.textView_authorName);
            time = (TextView) itemView.findViewById(R.id.textView_time);
            city = (TextView)itemView.findViewById(R.id.textView_cityName) ;
            view=(View)itemView.findViewById(R.id.listitemm);
            itemView.setOnClickListener(this);
        }
         void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }

        @Override
        public void onClick(View v) {
            //this.itemClickListener.onItemClick(v, getLayoutPosition());

        }
    }

}
