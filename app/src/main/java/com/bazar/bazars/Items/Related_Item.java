package com.bazar.bazars.Items;

/**
 * Created by AG on 4/11/2017.
 */

public class Related_Item {

     private String image;
    private String post_id,userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String sender_name) {
        this.image = sender_name;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String time_send) {
        this.post_id = time_send;
    }
}
