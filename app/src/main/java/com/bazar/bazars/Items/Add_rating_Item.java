package com.bazar.bazars.Items;

/**
 * Created by AG on 4/4/2017.
 */

public class Add_rating_Item {

    private String commenter ;
    private String date_comment ;
    private String comment ;


    public Add_rating_Item(String commenter, String date_comment, String comment) {
        this.commenter = commenter;
        this.date_comment = date_comment;
        this.comment = comment;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getDate_comment() {
        return date_comment;
    }

    public void setDate_comment(String date_comment) {
        this.date_comment = date_comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
