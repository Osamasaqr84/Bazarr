package com.bazar.bazars.Items;

/**
 * Created by AG on 3/28/2017.
 */

public class Details_Photo_item {
    private String photo_advertis ;

    public Details_Photo_item(String photo) {
        this.photo_advertis = photo
        ;
    }

    public String getPhoto() {
        return photo_advertis;
    }

    public void setPhoto(String photo) {
        this.photo_advertis = photo;
    }
}
