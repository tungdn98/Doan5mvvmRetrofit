package com.example.giaysnaker6789.network;

import com.example.giaysnaker6789.BaseResponse.BaseResponse;
import com.example.giaysnaker6789.BaseResponse.Billresponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIbill {
    @FormUrlEncoded
    @POST("api/CreateBill")
    Call<Billresponse> pushBill(@Field("idUser") int iduser,
                                @Field("nameUser") String nameuser,
                                @Field("idProduct") int idproduct,
                                @Field("price") int price,
                                @Field("count") int count,
                                @Field("status") String status,
                                @Field("voucher") String voucher,
                                @Field("billdate") String billdate);


    @FormUrlEncoded
    @POST("api/getBills")
    Call<Billresponse> getBill(@Field("idbill") int iduser);


    @FormUrlEncoded
    @POST("api/getCountBill")
    Call<Integer> getCountBill(@Field("idUser") int iduser, @Field("status") String stt);

    @FormUrlEncoded
    @POST("api/deletebills")
    Call<Billresponse> deleteBill(@Field("id") int id, @Field("idbill") int idbill,
                                  @Field("idProduct") int idProduct,
                                  @Field("count") int count);

    @FormUrlEncoded
    @POST("api/Updatebills")
    Call<Billresponse> updateBill(@Field("id") int iduser,
                                  @Field("idbill") int idproduct,
                                  @Field("count") int count,
                                  @Field("status") String status,
                                  @Field("idProduct") int idProduct,
                                  @Field("saled") int saled);

    @FormUrlEncoded
    @POST("api/Payment")
    Call<Billresponse> orderProduct
            (@Field("idbill") int idbill,
             @Field("status") String status);

    @FormUrlEncoded
    @POST("api/updateCountProduct")
    Call<BaseResponse> updateCountProduct
            (@Field("idpro") int idpro,
             @Field("count") int count);

    @FormUrlEncoded
    @POST("api/loadDetailBillWithID")
    Call<Billresponse> getBillWithID(@Field("idBill") int iduser);

}
