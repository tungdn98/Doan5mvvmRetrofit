package com.example.giaysnaker6789.viewModels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.BaseResponse.BaseResponseFeedback;
import com.example.giaysnaker6789.network.APIProduct;
import com.example.giaysnaker6789.network.APIfeedback;
import com.example.giaysnaker6789.network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackViewModel extends ViewModel {
    private APIfeedback dataClient;

    public MutableLiveData<BaseResponseFeedback> LoadListProductType(int idproducttype, int page){
        MutableLiveData<BaseResponseFeedback>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIfeedback.class);
        dataClient.getfeedback(idproducttype,page).enqueue(new Callback<BaseResponseFeedback>() {
            @Override
            public void onResponse(Call<BaseResponseFeedback> call, Response<BaseResponseFeedback> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BaseResponseFeedback> call, Throwable t) {

            }
        });
        return newsData;
    }

    public MutableLiveData<Integer> postFeedback(int idproduct,int idUser,String content,String feedate,float rate){
        MutableLiveData<Integer>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIfeedback.class);
        dataClient.pushFeedback(idproduct,idUser,content,feedate,rate).enqueue(new Callback<Integer>() {
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

}
