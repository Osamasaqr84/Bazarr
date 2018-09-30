package com.bazar.bazars.Items;

import java.util.ArrayList;

/**
 * Created by tournedo2003 on 3/9/17.
 */

public class Tab_one_items {
    private String id;

    public String getCategories() {
        return Categories;
    }

    public void setCategories(String categories) {
        Categories = categories;
    }

    private String Categories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    private String order;


    public void setSubCategories(ArrayList<Tab_two_item> subCategories) {
        SubCategories = subCategories;
    }

    public ArrayList<Tab_two_item> getSubCategories() {
        return SubCategories;
    }

    private ArrayList<Tab_two_item> SubCategories;

    public Tab_one_items(String id, String name, ArrayList<Tab_two_item> subCategories) {
        this.id = id;
        this.name = name;
        this.SubCategories = subCategories;
        this.order = order;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
