package com.bazar.bazars.Items;

/**
 * Created by tournedo2003 on 3/13/17.
 */

public class User_items {

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String name;
    private String mobile;

    public User_items(String id, String name, String mobile) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;


    }

}
