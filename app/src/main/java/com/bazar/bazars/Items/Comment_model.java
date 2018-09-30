package com.bazar.bazars.Items;

/**
 * Created by ali on 4/8/2017.
 */

public class Comment_model {

    String userName,commentDate,commentContent;

    public String getCommentContent() {
        return commentContent;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
