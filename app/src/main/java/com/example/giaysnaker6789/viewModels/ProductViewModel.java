package com.example.giaysnaker6789.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.network.APIProduct;
import com.example.giaysnaker6789.network.DataClient;
import com.example.giaysnaker6789.network.RetrofitService;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends ViewModel {
    private APIProduct dataClient;
    private MutableLiveData<products> mutableLiveData;

    public MutableLiveData<ProductBaseResponse> LoadProduct(int page,int type,int originid){
        MutableLiveData<ProductBaseResponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIProduct.class);
        dataClient.getListProduct(page,type,originid).enqueue(new Callback<ProductBaseResponse>() {
            @Override
            public void onResponse(Call<ProductBaseResponse> call, Response<ProductBaseResponse> response) {
                if(response.isSuccessful()){
                    newsData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<ProductBaseResponse> call, Throwable t) {

            }
        });
        return newsData;
    }

    public MutableLiveData<String> getCount(){
        MutableLiveData<String>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIProduct.class);
        dataClient.getCount().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        return newsData;
    }

    public MutableLiveData<ProductBaseResponse> LoadProductwithType(int id){
        MutableLiveData<ProductBaseResponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIProduct.class);
        dataClient.getproType(id).enqueue(new Callback<ProductBaseResponse>() {
            @Override
            public void onResponse(Call<ProductBaseResponse> call, Response<ProductBaseResponse> response) {
                newsData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<ProductBaseResponse> call, Throwable t) {

            }
        });
        return newsData;
    }


    public MutableLiveData<List<products>> searchProduct(String name){
        MutableLiveData<List<products>>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIProduct.class);
        dataClient.searchProduct(name).enqueue(new Callback<List<products>>() {
            @Override
            public void onResponse(Call<List<products>> call, Response<List<products>> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<products>> call, Throwable t) {
            }
        });
        return newsData;
    }

    public MutableLiveData<products> searchByid(String id){
        MutableLiveData<products>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIProduct.class);
        dataClient.searchByid(id).enqueue(new Callback<products>() {
            @Override
            public void onResponse(Call<products> call, Response<products> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<products> call, Throwable t) {

            }
        });
        return newsData;
    }



}
