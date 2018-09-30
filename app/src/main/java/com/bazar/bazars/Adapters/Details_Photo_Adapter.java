package com.bazar.bazars.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bazar.bazars.ItemClickListener;
import com.bazar.bazars.NewGallery;
import com.bazar.bazars.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by AG on 3/28/2017.
 */

public class Details_Photo_Adapter extends RecyclerView.Adapter<Details_Photo_Adapter.ViewHolder> {


    private Context context;
    private List<String> my_data_adv;
   // private View view;
    private ArrayList<Bitmap> images;
    private ArrayList<String> imag ;

//    private ArrayList<Uri> uris;
//    private GalleryPhoto galleryPhoto;
//    private  ArrayList<String> names;

    public Details_Photo_Adapter(Context context, List<String> my_data_adv) {
        this.context = context;
        this.my_data_adv = my_data_adv;
//        uris = new ArrayList<Uri>();
//        galleryPhoto = new GalleryPhoto(context);
//        names = new ArrayList<>();
         images = new ArrayList<>();
        imag = new ArrayList<>();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.addphotoitem, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startGalleryActivity();
//            }
//        });
        Glide.with(context)
                .load(my_data_adv.get(position))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        // you can do something with loaded bitmap here

                        // .....
                        holder.imageView.setImageBitmap(resource);
                        //BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        String filename = "bitmap.png" + String.valueOf(position);
                        FileOutputStream stream = null;
                        try {
                            stream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                            resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            stream.close();
                           // bitmap.recycle();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                                imag.add(filename);




                    }
                });
//        Glide.with(context)
//                .load(my_data_adv.get(position))
//                .downloadOnly(new SimpleTarget<File>() {
//                    @Override
//                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
//                        if(my_data_adv.get(position) != null
//                                && !my_data_adv.get(position).isEmpty()) {
//
//                            new ImageSaver(context).
//                                    setFileName("myImage").
//                                    setDirectoryName("bazarimages").
//                                    save(bitmap);
//                            bitmap = new ImageSaver(context).
//                                    setFileName("myImage").
//                                    setDirectoryName("bazarimages").
//                                    load();
//                            new ImageSaver(context).deletefile("myImage");
//                            Uri rui = getImageUri(context, bitmap);
//
//
//                            holder.imageView.setImageURI(rui);
//                        }
//                    }
//                });
        holder.exit.setVisibility(View.GONE);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(context, NewGallery.class);
                Collections.reverse(imag);
                intent.putExtra("photos",imag );
                context.startActivity(intent);
            }
        });
        }

    @Override
    public int getItemCount() {
        return my_data_adv.size();
    }
    public   class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        private String mItem;
        ImageButton exit;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.photo_advertis);
            exit = (ImageButton)itemView.findViewById(R.id.exitt);
        }

        void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());

        }
    }



    private void startGalleryActivity() {
        Intent intent = new Intent(context, NewGallery.class);
        intent.putParcelableArrayListExtra("photos",images);
        context.startActivity(intent);
        //BroadcastHelper.sendInform(context,"tilte",intent);
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
