package com.example.giaysnaker6789.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.BaseResponse.ResponseUser1s;
import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.network.ApiUser;
import com.example.giaysnaker6789.network.DataClient;
import com.example.giaysnaker6789.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private ApiUser dataClient;
    private MutableLiveData<user1s> mutableLiveData;

    public MutableLiveData<ResponseUser1s> loginHandle(String tk, String mk){
        MutableLiveData<ResponseUser1s>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(ApiUser.class);
        dataClient.login(tk,mk).enqueue(new Callback<ResponseUser1s>() {
            @Override
            public void onResponse(Call<ResponseUser1s> call, Response<ResponseUser1s> response) {
                if(response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ResponseUser1s> call, Throwable t) {

            }
        });

        return newsData;
    }

    public MutableLiveData<ResponseUser1s> getAccount(int id){
        MutableLiveData<ResponseUser1s>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(ApiUser.class);
        dataClient.getAccount(id).enqueue(new Callback<ResponseUser1s>() {
            @Override
            public void onResponse(Call<ResponseUser1s> call, Response<ResponseUser1s> response) {
                if(response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ResponseUser1s> call, Throwable t) {

            }
        });

        return newsData;
    }

    public MutableLiveData<ResponseUser1s> updateData(user1s us){
        MutableLiveData<ResponseUser1s>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(ApiUser.class);
        dataClient.updateUser(us).enqueue(new Callback<ResponseUser1s>() {
            @Override
            public void onResponse(Call<ResponseUser1s> call, Response<ResponseUser1s> response) {
                if(response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ResponseUser1s> call, Throwable t) {

            }
        });

        return newsData;
    }
}
