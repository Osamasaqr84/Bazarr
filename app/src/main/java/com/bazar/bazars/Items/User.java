package com.bazar.bazars.Items;

/**
 * Created by ali on 4/19/2017.
 */

public class User {

    String id,name,email,mobile,created_at,api_token,online;

    public void setName(String name) {
        this.name = name;
    }

    public String getApi_token() {
        return api_token;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getName() {
        return name;
    }

    public String getOnline() {
        return online;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setOnline(String online) {
        this.online = online;
    }

}
