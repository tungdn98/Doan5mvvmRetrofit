package com.example.giaysnaker6789.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.network.DataClient;
import com.example.giaysnaker6789.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private DataClient dataClient;
    private MutableLiveData<test> mutableLiveData;

    public MutableLiveData<List<test>> loginHandle(String tk, String mk){
        MutableLiveData<List<test>>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(DataClient.class);
        dataClient.login(tk,mk).enqueue(new Callback<List<test>>() {
            @Override
            public void onResponse(Call<List<test>> call, Response<List<test>> response) {
                ArrayList<test> list= (ArrayList<test>) response.body();
                if(list.size()>0){
                    newsData.setValue(list);
                }else{
                    test test1=new test(1,"","");
                    list.add(test1);
                    newsData.setValue(list);
                }

            }
            @Override
            public void onFailure(Call<List<test>> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }
}
