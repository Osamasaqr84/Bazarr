package com.bazar.bazars.Items;

public class Tab_three_item {

    /**
     * Created by tournedo2003 on 3/9/17.
     */


    private String id;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name ;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    private String order ;







    public Tab_three_item(String id, String name) {
        this.id = id;
        this.name = name;
        this.order = order;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}



