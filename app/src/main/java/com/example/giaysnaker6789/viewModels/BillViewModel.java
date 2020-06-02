package com.example.giaysnaker6789.viewModels;

import com.example.giaysnaker6789.BaseResponse.BaseResponse;
import com.example.giaysnaker6789.BaseResponse.Billresponse;
import com.example.giaysnaker6789.network.APIbill;
import com.example.giaysnaker6789.network.RetrofitService;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillViewModel extends ViewModel {

    private APIbill dataClient;


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

    public MutableLiveData<Billresponse> getBill(int idBill){
        MutableLiveData<Billresponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIbill.class);
        dataClient.getBill(idBill).enqueue(new Callback<Billresponse>() {
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


    public MutableLiveData<Billresponse> deleteBill(int id,int idbill){
        MutableLiveData<Billresponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIbill.class);
        dataClient.deleteBill(id,idbill).enqueue(new Callback<Billresponse>() {
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


    public MutableLiveData<Billresponse> updateBill(int id,int idbill,int count,String status){
        MutableLiveData<Billresponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIbill.class);
        dataClient.updateBill(id,idbill,count,status).enqueue(new Callback<Billresponse>() {
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


    public MutableLiveData<Billresponse> orDerBill(int idbill,String status){
        MutableLiveData<Billresponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIbill.class);
        dataClient.orderProduct(idbill, status).enqueue(new Callback<Billresponse>() {
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

    public MutableLiveData<BaseResponse> updateCountProduct(int idpro, int count){
        MutableLiveData<BaseResponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIbill.class);
        dataClient.updateCountProduct(idpro,count).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
        return newsData;
    }

    public MutableLiveData<Billresponse> getBillWithID(int idBill){
        MutableLiveData<Billresponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIbill.class);
        dataClient.getBillWithID(idBill).enqueue(new Callback<Billresponse>() {
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
