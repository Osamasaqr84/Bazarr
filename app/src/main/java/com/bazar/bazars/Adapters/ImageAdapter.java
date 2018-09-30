package com.bazar.bazars.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bazar.bazars.R;

/**
 * Created by AhmedDawoud on 09/04/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(240, 240));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundColor(Color.WHITE);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }


    private Integer[] mThumbIds = {
            R.drawable.ic_toyota, R.drawable.ic_ford,
            R.drawable.ic_chevrolet, R.drawable.ic_car_parts,
            R.drawable.ic_nissan, R.drawable.ic_mercides,
            R.drawable.ic_gmc, R.drawable.ic_bmw,
            R.drawable.ic_lexus, R.drawable.ic_jeep,
            R.drawable.ic_hunday, R.drawable.ic_honda,
            R.drawable.ic_hummer2x, R.drawable.ic_infiniti2x,
            R.drawable.ic_landrover, R.drawable.ic_mazda,
            R.drawable.ic_mercury, R.drawable.ic_vw,
            R.drawable.ic_mitsubishi, R.drawable.ic_lincoln,
            R.drawable.ic_opel, R.drawable.ic_isuzu,
            R.drawable.ic_porsche, R.drawable.ic_kia,
            R.drawable.ic_maserati, R.drawable.ic_bentli,
            R.drawable.ic_aston_martin, R.drawable.ic_cadillac,
            R.drawable.ic_chrysler, R.drawable.ic_citroen,
            R.drawable.ic_daewoo, R.drawable.ic_daihatsu,
            R.drawable.ic_dodge, R.drawable.ic_ferrari,
            R.drawable.ic_fiat, R.drawable.ic_jaguar,
            R.drawable.ic_lamborghini, R.drawable.ic_rollsroyce,
            R.drawable.ic_peugeot, R.drawable.ic_subaru,
            R.drawable.ic_suzuki2x, R.drawable.ic_volvo,
            R.drawable.ic_skoda, R.drawable.ic_audi,
            R.drawable.ic_renulat, R.drawable.ic_buick,
            R.drawable.ic_saab, R.drawable.ic_seat,
            R.drawable.ic_mg, R.drawable.ic_proton,

            R.drawable.ic_sangyong, R.drawable.ic_chery,
            R.drawable.ic_jeely, R.drawable.ic_zxauto,
            R.drawable.ic_bikes, R.drawable.ic_trucks,
            R.drawable.ic_daihatsu, R.drawable.ic_oldcars
            ,R.drawable.ic_damaged,R.drawable.tanazol


    };
}
