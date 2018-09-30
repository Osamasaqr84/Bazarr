package com.bazar.bazars.Items;

/**
 * Created by Ali on 5/7/2017.
 */

public class MainMenuItem {
    String name,value;
    public MainMenuItem(String name,String value){
        this.name = name;
        this.value=value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
