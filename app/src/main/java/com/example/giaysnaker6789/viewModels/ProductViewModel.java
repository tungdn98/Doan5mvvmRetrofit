package com.example.giaysnaker6789.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.network.DataClient;
import com.example.giaysnaker6789.network.RetrofitService;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends ViewModel {
    private DataClient dataClient;
    private MutableLiveData<products> mutableLiveData;

    public MutableLiveData<ProductBaseResponse> LoadProduct(int page){
        MutableLiveData<ProductBaseResponse>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(DataClient.class);
        dataClient.getListProduct(page).enqueue(new Callback<ProductBaseResponse>() {
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

    public MutableLiveData<String> getCount(){
        MutableLiveData<String>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(DataClient.class);
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

}
