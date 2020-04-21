package com.example.giaysnaker6789.network;
import com.example.giaysnaker6789.BaseResponse.Billresponse;
import com.example.giaysnaker6789.models.bills;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
                                    @Field("billdate") String billdate );


    @FormUrlEncoded
    @POST("api/getBill")
    Call<Billresponse> getBill(@Field("idUser") int iduser,
                               @Field("status") String status);

    @FormUrlEncoded
    @POST("api/getCountBill")
    Call<Integer> getCountBill(@Field("idUser") int iduser,@Field("status") String stt);

    @FormUrlEncoded
    @POST("api/deleteBill")
    Call<Integer> deleteBill(@Field("idUser") int iduser,@Field("idProduct") int idproduct);

    @FormUrlEncoded
    @POST("api/updateBill")
    Call<Billresponse> updateBill(@Field("idUser") int iduser,
                             @Field("idProduct") int idproduct,
                             @Field("count") int count,
                             @Field("status") String status);

    @FormUrlEncoded
    @POST("api/orderProduct")
    Call<Billresponse>orderProduct
            (@Field("idUser") int iduser,
             @Field("status") String status,
             @Field("statusUpdate") String statusUpdate);
}
