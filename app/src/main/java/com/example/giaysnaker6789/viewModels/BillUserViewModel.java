package com.example.giaysnaker6789.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.BaseResponse.BillUserResponse;
import com.example.giaysnaker6789.BaseResponse.bills2BaseResponse;
import com.example.giaysnaker6789.network.APIBillUser;
import com.example.giaysnaker6789.network.RetrofitService;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillUserViewModel extends ViewModel {

    private APIBillUser dataClient = RetrofitService.cteateService(APIBillUser.class);

    public MutableLiveData<BillUserResponse> getcountbill(int iduser, String status) {
        MutableLiveData<BillUserResponse> newsData = new MutableLiveData<>();
        dataClient.getcountBill(iduser, "b1").enqueue(new Callback<BillUserResponse>() {
            @Override
            public void onResponse(Call<BillUserResponse> call, Response<BillUserResponse> response) {
                if(response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BillUserResponse> call, Throwable t) {

            }
        });
        return newsData;
    }

    public MutableLiveData<BillUserResponse> AddtoCart(int iduser, String status, int price, int count, String orderdate,
                                                       String receiveddate, int idProduct, String voucher) {
        MutableLiveData<BillUserResponse> newsData = new MutableLiveData<>();
        dataClient.AddtoCart(iduser, status, price, count, orderdate,
                receiveddate, idProduct, voucher).enqueue(new Callback<BillUserResponse>() {
            @Override
            public void onResponse(Call<BillUserResponse> call, Response<BillUserResponse> response) {
                if(response.isSuccessful()){
                    newsData.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<BillUserResponse> call, Throwable t) {
//                BillUserResponse billUserResponse= new BillUserResponse();
//                billUserResponse.setStatus("FAIL");
//                billUserResponse.setStatus(""+t.getMessage());
//                billUserResponse.setData(null);
//                newsData.setValue(billUserResponse);
            }
        });
        return newsData;
    }

    public MutableLiveData<BillUserResponse> AddtoExCart(String status, int price, int count, String orderdate, int idProduct, String voucher, int idBill) {
        MutableLiveData<BillUserResponse> newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIBillUser.class);
        dataClient.AddtoExCart(status, price, count, orderdate,
                idProduct, voucher, idBill).enqueue(new Callback<BillUserResponse>() {
            @Override
            public void onResponse(Call<BillUserResponse> call, Response<BillUserResponse> response) {
                if(response.isSuccessful()){
                    newsData.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<BillUserResponse> call, Throwable t) {
                BillUserResponse billUserResponse = new BillUserResponse();
                billUserResponse.setStatus("FAIL");
                billUserResponse.setStatus("" + t.getMessage());
                billUserResponse.setData(null);
                newsData.setValue(billUserResponse);
            }
        });
        return newsData;
    }


    public MutableLiveData<bills2BaseResponse> getbilldetail(int iduser, String status) {
        MutableLiveData<bills2BaseResponse> newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIBillUser.class);
        dataClient.getbilldetail(iduser, status).enqueue(new Callback<bills2BaseResponse>() {
            @Override
            public void onResponse(Call<bills2BaseResponse> call, Response<bills2BaseResponse> response) {
                if(response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<bills2BaseResponse> call, Throwable t) {

            }
        });
        return newsData;
    }


}
