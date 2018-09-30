package com.bazar.bazars.Helper;

import com.bazar.bazars.Models.AlldataModel;
import com.bazar.bazars.Models.Delete_Ad;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("Advertises/alldata/{typrid}/{id}/{image}/{cityid}/{id2}.json")
    Call<AlldataModel> getAlldata(
            @Path("typrid") String typrid,
            @Path("id") String id,
            @Path("image") String image,
            @Path("cityid") String cityid,
            @Path("cityid") String id2
    );


    @POST("Advertises/delete/{ad_Id}.json")
    Call<Delete_Ad> deleteAd(
            @Path(value = "ad_Id", encoded = true) String ad_Id
    );


}

