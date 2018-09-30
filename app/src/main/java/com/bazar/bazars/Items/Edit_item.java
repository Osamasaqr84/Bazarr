package com.bazar.bazars.Items;

import java.util.ArrayList;

/**
 * Created by Ali on 5/1/2017.
 */

public class Edit_item {

    String id,title,content,mobile;
    ArrayList<Edit_photo_item> images;

    public ArrayList<Edit_photo_item> getImages() {
        return images;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImages(ArrayList<Edit_photo_item> images) {
        this.images = images;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
