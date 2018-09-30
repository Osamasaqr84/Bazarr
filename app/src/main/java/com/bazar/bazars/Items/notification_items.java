package com.bazar.bazars.Items;

/**
 * Created by tournedo2003 on 3/31/17.
 */

public class notification_items {

    private String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotifiable_type() {
        return notifiable_type;
    }

    public void setNotifiable_type(String notifiable_type) {
        this.notifiable_type = notifiable_type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String type;
    private String notifiable_id;
    private String notifiable_type;
    private String data;
    private String read_at;
    private String created_at;
    private String post_id;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getRead_at() {
        return read_at;
    }

    public void setRead_at(String read_at) {
        this.read_at = read_at;
    }

    public String getMosified_id() {
        return mosified_id;
    }

    public void setMosified_id(String mosified_id) {
        this.mosified_id = mosified_id;
    }

    private String mosified_id;
    public notification_items(String id, String type, String notifiable_id, String notifiable_type, String data , String created_at, String mosified_id, String read_at, String postid) {
        this.id = id;
        this.type = type;
        this.notifiable_id = notifiable_id;
        this.notifiable_type = notifiable_type;
        this.data = data;
        this.post_id = postid;
        this.created_at = created_at;
        this.mosified_id = mosified_id;
        this.read_at = read_at;

    }

    public String getNotifiable_id() {
        return notifiable_id;
    }

    public void setNotifiable_id(String notifiable_id) {
        this.notifiable_id = notifiable_id;
    }
}
