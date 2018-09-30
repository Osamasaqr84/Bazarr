package com.bazar.bazars.Items;

/**
 * Created by AG on 4/11/2017.
 */

public class Comment_Item {

    private String comment_title;
    private String sender_name;
    private String time_send;
    private int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getComment_title() {

        return comment_title;
    }

    public void setComment_title(String comment_title) {
        this.comment_title = comment_title;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getTime_send() {
        return time_send;
    }

    public void setTime_send(String time_send) {
        this.time_send = time_send;
    }
}
