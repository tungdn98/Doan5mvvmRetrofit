package com.example.giaysnaker6789.network;

import com.example.giaysnaker6789.models.banners;
import com.example.giaysnaker6789.models.test;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataClient {

    @GET("api/getBanner")
    Call<List<banners>> getBanner();

    //@Headers("Content-Type: application/json")
    @POST("api/testne")
    Call<String> testphat(@Body List<test> test);


}
