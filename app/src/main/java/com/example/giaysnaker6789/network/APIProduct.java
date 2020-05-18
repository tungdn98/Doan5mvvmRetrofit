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

    @FormUrlEncoded
    @POST("api/getAll1")
    Call<ProductBaseResponse> getListProduct(@Query("page") int page,@Field("typesort") int type,@Field("origin") int originid);

    @FormUrlEncoded
    @POST("api/getproType")
    Call<ProductBaseResponse> getproType(@Field("idProductType") int id);

    @FormUrlEncoded
    @POST("api/getSPLQ")
    Call<ProductBaseResponse> getSPLQ(@Field("idProductype") int id,@Query("page") int page);

    @GET("api/getCount")
    Call<String> getCount();

    @FormUrlEncoded // seảch sản phẩm theo tên hoặc theo description
    @POST("api/searchProduct")
    Call<List<products>> searchProduct(@Field("name") String name);

    @FormUrlEncoded //
    @POST("api/searchByid")
    Call<products> searchByid(@Field("id") String id);
}
