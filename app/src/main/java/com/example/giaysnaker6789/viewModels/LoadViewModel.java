package com.example.giaysnaker6789.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.network.APIProduct;
import com.example.giaysnaker6789.network.DataClient;
import com.example.giaysnaker6789.network.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadViewModel extends ViewModel {

    public MutableLiveData<List<products>> userList;
    private APIProduct dataClient;
    public LoadViewModel() {
    }
    public MutableLiveData<ProductBaseResponse> LoadProduct(int page,int type,int originid){
        MutableLiveData<ProductBaseResponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIProduct.class);
        dataClient.getListProduct(page,type,originid).enqueue(new Callback<ProductBaseResponse>() {
            @Override
            public void onResponse(Call<ProductBaseResponse> call, Response<ProductBaseResponse> response) {
                if (response.isSuccessful()) {
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProductBaseResponse> call, Throwable t) {

            }
        });
        return newsData;
    }
}