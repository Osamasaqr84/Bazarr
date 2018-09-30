package com.bazar.bazars.Items;

import java.io.Serializable;

/**
 * Created by ali on 4/5/2017.
 */

public class AdDetails implements Serializable {

    private int adId;
    private String title,content,imageone,mobile,createdDate,username,city,nextpageUrl,PreviousePageUrl,total,
            perpage,currentpage,lastpage,from,to,userId, cityid,cat1,cat2,cat3;

    public String getCityid() {
        return cityid;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public void setCityid(String cityid1){
        this.cityid = cityid1;
    }

    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }

    public String getUserId() {
        return userId;
    }

    public String getCity() {
        return city;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getCurrentpage() {
        return currentpage;
    }

    public String getFrom() {
        return from;
    }

    public String getImageone() {
        return imageone;
    }

    public String getLastpage() {
        return lastpage;
    }

    public String getMobile() {
        return mobile;
    }

    public String getNextpageUrl() {
        return nextpageUrl;
    }

    public String getPerpage() {
        return perpage;
    }

    public String getPreviousePageUrl() {
        return PreviousePageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getTo() {
        return to;
    }

    public String getTotal() {
        return total;
    }

    public String getUsername() {
        return username;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setImageone(String imageone) {
        this.imageone = imageone;
    }

    public void setLastpage(String lastpage) {
        this.lastpage = lastpage;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setNextpageUrl(String nextpageUrl) {
        this.nextpageUrl = nextpageUrl;
    }

    public void setPerpage(String perpage) {
        this.perpage = perpage;
    }

    public void setPreviousePageUrl(String previousePageUrl) {
        PreviousePageUrl = previousePageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
