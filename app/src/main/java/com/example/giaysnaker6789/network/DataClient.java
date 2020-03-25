package com.example.giaysnaker6789.network;
import com.example.giaysnaker6789.models.test;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
public interface DataClient {
    @FormUrlEncoded
    @POST("api/login")
    Call<List<test>> login(@Field("tk") String tk, @Field("mk") String mk);
}
