package com.example.giaysnaker6789.network;
import com.example.giaysnaker6789.models.user1s;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
public interface ApiUser {
    @FormUrlEncoded
    @POST("api/loginNomal")
    Call<user1s> login(@Field("account") String tk, @Field("password") String mk);
}
