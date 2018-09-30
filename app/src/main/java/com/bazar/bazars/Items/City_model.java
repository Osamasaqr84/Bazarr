package com.bazar.bazars.Items;

/**
 * Created by ali on 4/10/2017.
 */

public class City_model {
    public String id,name;


    public City_model(String id, String name){
        this.id = id ;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

