package com.bazar.bazars.Connect_TO_Server;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tournedo2003 on 4/4/17.
 */

public class ServerResponse {
    // variable name should be same as in the json response from php    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    boolean getSuccess() {
        return success;
    }


}