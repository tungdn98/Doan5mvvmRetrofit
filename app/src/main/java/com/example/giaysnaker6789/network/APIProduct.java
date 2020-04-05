package com.example.giaysnaker6789.network;
import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.models.products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
public interface APIProduct {
    @POST("api/getProduct")
    Call<List<products>> getProduct();

    @GET("api/getAll1")
    Call<ProductBaseResponse> getListProduct(@Query("page") int page);

    @FormUrlEncoded
    @POST("api/getproType")
    Call<ProductBaseResponse> getproType(@Field("idProductType") int id);

    @GET("api/getCount")
    Call<String> getCount();
}
