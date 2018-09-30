package com.bazar.bazars.Items;

import android.net.Uri;

/**
 * Created by Ali on 5/1/2017.
 */

public class Edit_photo_item {
    String exit;
    String image;
    Uri rui;

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public Uri getRui() {
        return rui;
    }

    public void setRui(Uri rui) {
        this.rui = rui;
    }
}
