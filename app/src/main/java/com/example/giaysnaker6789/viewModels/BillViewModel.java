package com.example.giaysnaker6789.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.BaseResponse.Billresponse;
import com.example.giaysnaker6789.models.bills;
import com.example.giaysnaker6789.network.APIbill;
import com.example.giaysnaker6789.network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillViewModel extends ViewModel {

    private APIbill dataClient;

    public MutableLiveData<Billresponse> CreateBill(int idUser,String nameuser,int idproduct,int price,int count,String stt,String vou,String billdate){
        MutableLiveData<Billresponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIbill.class);
        dataClient.pushBill(idUser,nameuser,idproduct,price,count,stt,vou,billdate).enqueue(new Callback<Billresponse>() {
            @Override
            public void onResponse(Call<Billresponse> call, Response<Billresponse> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Billresponse> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t);
            }
        });
        return newsData;
    }

    public MutableLiveData<Integer> getcount(int iduser,String status){
        MutableLiveData<Integer>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIbill.class);
        dataClient.getCountBill(iduser,status).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        return newsData;
    }

    public MutableLiveData<Billresponse> getBill(int iduser){
        MutableLiveData<Billresponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIbill.class);
        dataClient.getBill(iduser,"b1").enqueue(new Callback<Billresponse>() {
            @Override
            public void onResponse(Call<Billresponse> call, Response<Billresponse> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Billresponse> call, Throwable t) {

            }
        });
        return newsData;
    }


    public MutableLiveData<Integer> deleteBill(int iduser,int idproduct){
        MutableLiveData<Integer>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIbill.class);
        dataClient.deleteBill(iduser,idproduct).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        return newsData;
    }


    public MutableLiveData<Billresponse> updateBill(int iduser,int idproduct,int count,String status){
        MutableLiveData<Billresponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIbill.class);
        dataClient.updateBill(iduser,idproduct,count,status).enqueue(new Callback<Billresponse>() {
            @Override
            public void onResponse(Call<Billresponse> call, Response<Billresponse> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Billresponse> call, Throwable t) {

            }
        });
        return newsData;
    }


    public MutableLiveData<Billresponse> orDerBill(int iduser,String status,String statusUpdate){
        MutableLiveData<Billresponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIbill.class);
        dataClient.orderProduct(iduser, status, statusUpdate).enqueue(new Callback<Billresponse>() {
            @Override
            public void onResponse(Call<Billresponse> call, Response<Billresponse> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Billresponse> call, Throwable t) {

            }
        });
        return newsData;
    }


}
