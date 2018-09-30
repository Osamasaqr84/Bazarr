package com.bazar.bazars.Connect_TO_Server;

/**
 * Created by tournedo2003 on 4/4/17.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shaon on 12/3/2016.
 */

public class ApiClient {
    public static final String BASE_URL = "http://www.mytriangle.net16.net";
    private static Retrofit retrofit = null;
    public static int unique_id;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}