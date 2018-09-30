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

public interface ApiServiceEdit {
 //    @Multipart
//    @POST("/retrofit_tutorial/retrofit_client.php")
//    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);
@Headers({
        "Accept: application/json",

})
    @Multipart
    @POST("?api_token")
    Call<ResponseBody> uploadAlbums(

        @Part("category_1") RequestBody description,
        @Part("category_2") RequestBody category_2,
        @Part("category_3") RequestBody category_3,
        @Part("city") RequestBody city,
        @Part("year") RequestBody year,
        @Part("title") RequestBody title,
        @Part("content") RequestBody content,
        @Part("mobile") RequestBody mobile,
        @Part("api_token") RequestBody api_token,

        @Part List<MultipartBody.Part> files

);

}