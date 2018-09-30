package com.bazar.bazars.Items;

import java.util.ArrayList;

/**
 * Created by tournedo2003 on 3/9/17.
 */

public class Tab_two_item {

    /**
     * Created by tournedo2003 on 3/9/17.
     */


    private String id;



    public String getCategories() {
        return Categories;
    }

    public void setCategories(String categories) {
        Categories = categories;
    }

    private String Categories ;

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


    public ArrayList<Tab_three_item> getSubCategoriess() {
        return SubCategoriess;
    }

    public void setSubCategoriess(ArrayList<Tab_three_item> subCategoriess) {
        SubCategoriess = subCategoriess;
    }

    private ArrayList<Tab_three_item> SubCategoriess;


    private String SubCategories;

    public Tab_two_item(String id, String name,  ArrayList<Tab_three_item> subCategories) {
        this.id = id;
        this.name = name;
        this.SubCategoriess = subCategories;
        this.order = order;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
