package com.bazar.bazars.Helper;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ali on 1/24/2018.
 */

public class ApiClient {

   // public static final String BASE_URL = "http://naqql.com/api/";
    public static final String BASE_URL = "http://alsog.net/api/";
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder();
//                    .header("Accept",
//                    Credentials.basic("username", "password"));

            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }
    }).build();


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
                  }

        return retrofit;
    }
}