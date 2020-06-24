package com.example.giaysnaker6789.network;
import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.models.product_types;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
public interface APIProducttype {
    @GET("api/getAllLoaiSp")
    Call<List<product_types>> getallloaisp();
}
