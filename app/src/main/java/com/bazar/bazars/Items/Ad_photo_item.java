package com.bazar.bazars.Items;

import android.net.Uri;

/**
 * Created by Ali on 5/1/2017.
 */

public class Ad_photo_item {
    String exit;
    Uri image;

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public Uri getImage() {
        return image;
    }
}
