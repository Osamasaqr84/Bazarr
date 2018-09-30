package com.bazar.bazars.Connect_TO_Server;

import org.json.JSONException;

/**
 * Created by AG on 1/29/2017.
 */

public interface VolleyCallback{
    public void onSuccess(String result) throws JSONException;
    public void onError(String error);
}