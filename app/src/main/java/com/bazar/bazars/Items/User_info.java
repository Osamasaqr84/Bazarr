package com.bazar.bazars.Items;

/**
 * Created by ali on 4/19/2017.
 */

public class User_info {
    String name,info,created_at;
    int online=0;
    public void setOnline(int online) {
        this.online = online;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getOnline() {
        return online;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setName(String name) {
        this.name = name;
    }
}

