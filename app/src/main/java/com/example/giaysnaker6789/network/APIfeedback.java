package com.example.giaysnaker6789.network;

import com.example.giaysnaker6789.BaseResponse.BaseResponseFeedback;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIfeedback {
    @FormUrlEncoded
    @POST("api/getfeedback")
    Call<BaseResponseFeedback> getfeedback(@Field("idProduct") int idproduct, @Query("page") int page);
}
