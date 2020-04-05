package com.example.giaysnaker6789.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    public MutableLiveData<user1s> loginHandle(String tk, String mk){
        MutableLiveData<user1s>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(ApiUser.class);
        dataClient.login(tk,mk).enqueue(new Callback<user1s>() {
            @Override
            public void onResponse(Call<user1s> call, Response<user1s> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<user1s> call, Throwable t) {
                newsData.setValue(null);
            }
        });

        return newsData;
    }
}
