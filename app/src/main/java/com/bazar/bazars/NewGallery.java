package com.bazar.bazars;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bazar.bazars.Adapters.SlidingImage_Adapter;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

public class NewGallery extends AppCompatActivity {
    private  ViewPager mPager;
    private  int currentPage = 0;
    private  int NUM_PAGES = 0;
    private  ArrayList<Bitmap> IMAGES ;
    private ArrayList<Parcelable> ImagesArray = new ArrayList<Parcelable>();
    PreferenceHelper helper;
    Bitmap bitmap;
    ArrayList<String> phot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gallery);
        IMAGES = new ArrayList<Bitmap>();
        helper = new PreferenceHelper(this);
        phot = new ArrayList<String>();
//        byte[] byteArray = getIntent().getByteArrayExtra("photos");
//
//        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);;
//            IMAGES.add(0,bitmap);
        bitmap = null;
        phot = getIntent().getStringArrayListExtra("photos");
        for (int i = 0;i<phot.size();i++){
            String filename = phot.get(i);
            try {
                FileInputStream is = this.openFileInput(filename);
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
                IMAGES.add(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        init();
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void init() {


       // for (int i = 0; i < ImagesArray.size(); i++)


        mPager = (ViewPager) findViewById(R.id.pager);
                mPager.setAdapter(new SlidingImage_Adapter(NewGallery.this, IMAGES));
                CirclePageIndicator indicator = (CirclePageIndicator)
                        findViewById(R.id.indicator);
                indicator.setViewPager(mPager);
                final float density = getResources().getDisplayMetrics().density;
                indicator.setRadius(5 * density);
                NUM_PAGES = IMAGES.size();
                // Auto start of viewpager
                final Handler handler = new Handler();
//                final Runnable Update = new Runnable() {
//                     public void run() {
//                        if (currentPage == NUM_PAGES) {
//                            currentPage = 0;
//                        }
//                        mPager.setCurrentItem(currentPage++, true);
//                    }
//                };
//                Timer swipeTimer = new Timer();
//                swipeTimer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        handler.post(Update);
//                    }
//                }, 3000, 3000);

                // Pager listener over indicator
                indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        currentPage = position;

                    }

                    @Override
                    public void onPageScrolled(int pos, float arg1, int arg2) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int pos) {

                    }
                });


      //  }
    }
}
