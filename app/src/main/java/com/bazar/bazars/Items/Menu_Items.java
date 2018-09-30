package com.bazar.bazars.Items;

/**
 * Created by AhmedDawoud on 09/04/2016.
 */
public class Menu_Items {

    private String titles ;
    private int images ;
    private String notif_val;
    boolean notification = false;

    public Menu_Items(){}

    public Menu_Items(String titles, String notif_val, boolean notification) {
        this.titles = titles;
        this.images = images;
        this.notif_val = notif_val;
        this.notification = notification;
    }

    public String getNotif_val() {
        return notif_val;
    }

    public void setNotif_val(String notif_val) {
        this.notif_val = notif_val;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }
}
