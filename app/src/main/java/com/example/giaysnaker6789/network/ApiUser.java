package com.example.giaysnaker6789.network;

import com.example.giaysnaker6789.BaseResponse.ResponseUser1s;
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
    Call<ResponseUser1s> login(@Field("account") String tk, @Field("password") String mk);

    @FormUrlEncoded
    @POST("api/RegisterNomal")
    Call<ResponseUser1s> RegisternoMal(@Field("account") String tk,
                                       @Field("password") String password,
                                       @Field("address") String address,
                                       @Field("phone") String phone,
                                       @Field("name") String name,
                                       @Field("imagefb") String imagefb);

    @FormUrlEncoded
    @POST("api/RegisterFacebook")
    Call<ResponseUser1s> RegisterFacebook(@Field("idfb") String idfb,
                                          @Field("fbName") String fbname,
                                          @Field("imagefb") String imgfb,
                                          @Field("address") String address,
                                          @Field("phone") String phone);

    @FormUrlEncoded
    @POST("api/ConnectWithFacebook")
    Call<ResponseUser1s> ConnectWithFacebook(@Field("id") int id,
                                             @Field("idfb") String idfb,
                                             @Field("fbName") String fbname,
                                             @Field("imagefb") String imgfb,
                                             @Field("address") String address,
                                             @Field("phone") String phone);

    @FormUrlEncoded
    @POST("api/getAccount")
    Call<ResponseUser1s> getAccount(@Field("id") int id);


}
