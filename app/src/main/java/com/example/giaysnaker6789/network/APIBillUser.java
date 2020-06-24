package com.example.giaysnaker6789.network;

import com.example.giaysnaker6789.BaseResponse.BillUserResponse;
import com.example.giaysnaker6789.BaseResponse.Billresponse;
import com.example.giaysnaker6789.BaseResponse.bills2BaseResponse;
import com.example.giaysnaker6789.models.bills;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIBillUser {
    @FormUrlEncoded
    @POST("api/getCountBill")
    Call<BillUserResponse> getcountBill(@Field("idUser") int iduser,
                                        @Field("status") String status);

    @FormUrlEncoded
    @POST("api/AddtoCart")
    Call<BillUserResponse> AddtoCart(@Field("idUser") int iduser,
                                      @Field("status") String status,
                                      @Field("price") int price,
                                      @Field("count") int count,
                                      @Field("orderdate") String orderdate,
                                      @Field("receiveddate") String receiveddate,
                                      @Field("idProduct") int idproduct,
                                      @Field("voucher") String voucher );

    @FormUrlEncoded
    @POST("api/AddtoExCart")
    Call<BillUserResponse> AddtoExCart(
                                     @Field("status") String status,
                                     @Field("price") int price,
                                     @Field("count") int count,
                                     @Field("orderdate") String orderdate,
                                     @Field("idProduct") int idproduct,
                                     @Field("voucher") String voucher,
                                     @Field("idBill") int idBill);

    @FormUrlEncoded
    @POST("api/getorder")
    Call<bills2BaseResponse> getbilldetail(
            @Field("idUser") int iduser,
            @Field("status") String status );


}
