package com.example.giaysnaker6789.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.network.APIimage;
import com.example.giaysnaker6789.network.ApiUser;
import com.example.giaysnaker6789.network.RetrofitService;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewmodel extends ViewModel {
    private ApiUser dataClient;
    private APIimage apiImage;

    public MutableLiveData<user1s> RegisterNomal(String tk,String mk,String dc,String phone,String name,String path){
        MutableLiveData<user1s>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(ApiUser.class);
        dataClient.RegisternoMal(tk,mk,dc,phone,name,path).enqueue(new Callback<user1s>() {
            @Override
            public void onResponse(Call<user1s> call, Response<user1s> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<user1s> call, Throwable t) {
                newsData.setValue(new user1s());
            }
        });

        return newsData;
    }

    public MutableLiveData<String> UploadImage(MultipartBody.Part path){
        MutableLiveData<String>  newsData = new MutableLiveData<>();
        apiImage = RetrofitService.cteateService(APIimage.class);
        apiImage.uploadFile(path).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                newsData.setValue("");
            }
        });
        return newsData;
    }

}
