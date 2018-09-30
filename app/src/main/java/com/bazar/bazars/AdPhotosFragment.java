package com.bazar.bazars;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import junit.framework.Assert;

import java.util.ArrayList;

import javax.xml.transform.TransformerException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ali on 4/4/2017.
 */

public class AdPhotosFragment extends BaseFragment {
    MainActivity activity;
    View view;
    ImageView imageView;
    public static final String TAG = "GalleryActivity";
    public static final String EXTRA_NAME = "images";

    ArrayList<String> images = new ArrayList<String>();

    @InjectView(R.id.pager)
    ViewPager _pager;
    @InjectView(R.id.thumbnails)
    LinearLayout _thumbnails;
    @InjectView(R.id.btn_close)
    ImageButton _closeButton;


    public static AdPhotosFragment newInstance() {

        Bundle args = new Bundle();

        AdPhotosFragment fragment = new AdPhotosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.adphotosfragment, container, false);
       // LinearLayout layout = (LinearLayout)view. findViewById(R.id.linear);
        activity = new MainActivity();
        ButterKnife.inject(activity);


        return view;
    }


    @Override
    protected boolean isValidate() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public boolean OnBackPressed() {
        //AdDetailsFragment frag = AdDetailsFragment.newInstance();
        //activity.addFragment(frag,false,"adDetail");
        return super.OnBackPressed();
    }

    @Override
    public void onClick(View v) {

    }
    public void startGalleryActivity() {
        images.add("http://sourcey.com/images/stock/salvador-dali-metamorphosis-of-narcissus.jpg");
        images.add("http://sourcey.com/images/stock/salvador-dali-the-dream.jpg");
        images.add("http://sourcey.com/images/stock/salvador-dali-persistence-of-memory.jpg");
        images.add("http://sourcey.com/images/stock/simpsons-persistence-of-memory.jpg");
        images.add("http://sourcey.com/images/stock/salvador-dali-the-great-masturbator.jpg");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startGalleryActivity();
        //_images = (ArrayList<String>) activity.getIntent().getSerializableExtra(EXTRA_NAME);
        Assert.assertNotNull(images);

        GalleryPagerAdapter _adapter = new GalleryPagerAdapter(activity);
        _pager.setAdapter(_adapter);
        _pager.setOffscreenPageLimit(6); // how many images to load into memory

        _closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Close clicked");
                activity.onBackPressed();
            }
        });
    }

    @Override
    public void warning(TransformerException exception) throws TransformerException {

    }
    class GalleryPagerAdapter extends PagerAdapter {

        Context _context;
        LayoutInflater _inflater;

        public GalleryPagerAdapter(Context context) {
            _context = context;
            _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = _inflater.inflate(R.layout.pager_gallery_item, container, false);
            container.addView(itemView);

            // Get the border size to show around each image
            int borderSize = _thumbnails.getPaddingTop();

            // Get the size of the actual thumbnail image
            int thumbnailSize = ((FrameLayout.LayoutParams)
                    _pager.getLayoutParams()).bottomMargin - (borderSize*2);

            // Set the thumbnail layout parameters. Adjust as required
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
            params.setMargins(0, 0, borderSize, 0);

            // You could also set like so to remove borders
            //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
            //        ViewGroup.LayoutParams.WRAP_CONTENT,
            //        ViewGroup.LayoutParams.WRAP_CONTENT);

            final ImageView thumbView = new ImageView(_context);
            thumbView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumbView.setLayoutParams(params);
            thumbView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Thumbnail clicked");

                    // Set the pager position when thumbnail clicked
                    _pager.setCurrentItem(position);
                }
            });
            _thumbnails.addView(thumbView);

            final SubsamplingScaleImageView imageView =
                    (SubsamplingScaleImageView) itemView.findViewById(R.id.image);

            // Asynchronously load the image and set the thumbnail and pager view
            Glide.with(_context)
                    .load(images.get(position))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            imageView.setImage(ImageSource.bitmap(bitmap));
                            thumbView.setImageBitmap(bitmap);
                        }
                    });

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
    @Override
    public void error(TransformerException exception) throws TransformerException {

    }

    @Override
    public void fatalError(TransformerException exception) throws TransformerException {

    }
}
