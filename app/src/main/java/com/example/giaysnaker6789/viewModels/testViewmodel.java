package com.example.giaysnaker6789.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.network.DataClient;
import com.example.giaysnaker6789.network.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class testViewmodel extends ViewModel {
    private DataClient dataClient;
    private MutableLiveData<test> mutableLiveData;


    public MutableLiveData<List<test>> loginHandle(String tk, String mk){
        MutableLiveData<List<test>>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(DataClient.class);
        dataClient.login(tk,mk).enqueue(new Callback<List<test>>() {
            @Override
            public void onResponse(Call<List<test>> call, Response<List<test>> response) {
                newsData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<List<test>> call, Throwable t) {
               newsData.setValue(null);
            }
        });
        return newsData;
    }

    public MutableLiveData<Integer> insertHandle(String tk, String mk){
        MutableLiveData<Integer>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(DataClient.class);
        dataClient.insert(tk,mk).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }


}
