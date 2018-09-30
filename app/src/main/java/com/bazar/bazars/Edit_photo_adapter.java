package com.bazar.bazars;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import com.bazar.bazars.Items.Edit_photo_item;

import static android.content.ContentValues.TAG;

/**
 * Created by AG on 3/28/2017.
 */

public class Edit_photo_adapter extends RecyclerView.Adapter<Edit_photo_adapter.ViewHolder> {


        private Context context;
        private ArrayList<Edit_photo_item> my_data_adve;
        private View view;
        private LayoutInflater mInflater;
        ImageSaver saver;
        int p = 0;
        public Edit_photo_adapter(Context context, ArrayList<Edit_photo_item> my_data_adv){
            this.context = context;
            this.my_data_adve = my_data_adv;
            mInflater = LayoutInflater.from(context);
            saver = new ImageSaver(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.addphotoitem, parent, false);
            ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if(my_data_adve.get(position).getImage() != null &&!my_data_adve.get(position).getImage().isEmpty()){

            Glide.with(context)
                    .load(my_data_adve.get(position).getImage())
                    .downloadOnly(new SimpleTarget<File>() {
                        @Override
                        public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                            if(my_data_adve.get(position).getImage() != null
                                    && !my_data_adve.get(position).getImage().isEmpty()) {
                                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                                Bitmap bitmap = BitmapFactory.decodeFile(resource.getAbsolutePath(), bmOptions);
                                new ImageSaver(context).
                                        setFileName("myImage").
                                        setDirectoryName("bazarimages").
                                        save(bitmap);
                                bitmap = new ImageSaver(context).
                                        setFileName("myImage").
                                        setDirectoryName("bazarimages").
                                        load();
                                new ImageSaver(context).deletefile("myImage");
                                Uri rui;
                                rui = getImageUri(context, bitmap);
                                my_data_adve.get(position).setRui(rui);
                                my_data_adve.get(position).setImage(null);
                                holder.imageView.setImageURI(my_data_adve.get(position).getRui());

                            }
                        }
                    });
            }else {
                holder.imageView.setImageURI(my_data_adve.get(position).getRui());

            }
            holder.exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pushIntent = new Intent("walker_status");
                    pushIntent.putExtra("walker_status", "a");
                    if(position!=0) {
                        pushIntent.putExtra("position", position);
                    }else {
                        pushIntent.putExtra("position", 0);
                    }
                    BroadcastHelper.sendInform(context, "titles",pushIntent);
                    //my_data_adve.remove(my_data_adve.get(position));

                }
            });
        }


    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
        @Override
        public int getItemCount() {
            return my_data_adve.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private ImageView imageView;
            private String mItem;
            ImageButton exit;

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                imageView = (ImageView) itemView.findViewById(R.id.photo_advertis);
                exit = (ImageButton)itemView.findViewById(R.id.exitt);
             }

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick " + getPosition() + "" + mItem);

            }
        }
}
