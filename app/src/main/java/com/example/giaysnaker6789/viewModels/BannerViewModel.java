package com.example.giaysnaker6789.viewModels;

import com.example.giaysnaker6789.models.banners;
import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.network.APIUtils;
import com.example.giaysnaker6789.network.BaseService;
import com.example.giaysnaker6789.network.DataClient;
import com.example.giaysnaker6789.network.RetrofitService;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannerViewModel extends ViewModel {
    private DataClient dataClient = APIUtils.getData();;
//    public MutableLiveData<List<banners>> getBanners(){
//        MutableLiveData<List<banners>>  newsData = new MutableLiveData<>();
//        dataClient = RetrofitService.cteateService(DataClient.class);
//        dataClient.getBanner().enqueue(new Callback<List<banners>>() {
//            @Override
//            public void onResponse(Call<List<banners>> call, Response<List<banners>> response) {
//                newsData.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<banners>> call, Throwable t) {
//
//            }
//        });
//        return newsData;
//    }

    public MutableLiveData<String> testne(List<test>list){
        MutableLiveData<String>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(DataClient.class);
        dataClient.testphat(list).enqueue(new Callback<String>() {
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

    public MutableLiveData<List<banners>> getBanners(){
        MutableLiveData<List<banners>>  newsData = new MutableLiveData<>();
        dataClient.getBanner().enqueue(new Callback<List<banners>>() {
            @Override
            public void onResponse(Call<List<banners>> call, Response<List<banners>> response) {
                newsData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<List<banners>> call, Throwable t) {
            }
        });
        return newsData;
    }

}
