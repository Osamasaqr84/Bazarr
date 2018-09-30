package com.bazar.bazars.Connect_TO_Server;

/**
 * Created by tournedo2003 on 4/4/17.
 */

/**
 * Created by Shaon on 12/3/2016.
 */

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
//    @Multipart
//    @POST("/retrofit_tutorial/retrofit_client.php")
//    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);
@Headers({
        "Accept: application/json"
})
    @Multipart
    @POST("advertises/add")
    Call<ResponseBody> uploadAlbums(

        @Part("category_id") RequestBody description,
        @Part("sub_category_id") RequestBody category_2,
        @Part("sub_mini_category_id") RequestBody category_3,
        @Part("city_id") RequestBody city,
        @Part("year") RequestBody year,
        @Part("name") RequestBody title,
        @Part("content") RequestBody content,
        @Part("user_id") RequestBody api_token,
        @Part("mobile") RequestBody mobile,

        @Part List<MultipartBody.Part> files

);
    @Multipart
    @POST("_")
    Call<ResponseBody> edit(

            @Part("category_id") RequestBody description,
            @Part("sub_category_id") RequestBody category_2,
            @Part("sub_mini_category_id") RequestBody category_3,
            @Part("city_id") RequestBody city,
             @Part("name") RequestBody title,
            @Part("content") RequestBody content,
            @Part("user_id") RequestBody api_token,
            @Part("mobile") RequestBody mobile,

            @Part List<MultipartBody.Part> files

    );
}