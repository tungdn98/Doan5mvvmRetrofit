package com.example.giaysnaker6789.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.BaseResponse.ResponseUser1s;
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

    public MutableLiveData<ResponseUser1s> RegisterNomal(String tk,String mk,String email,String dc,String phone,String name,String path){
        MutableLiveData<ResponseUser1s>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(ApiUser.class);
        dataClient.RegisternoMal(tk,mk,email,dc,phone,name,path).enqueue(new Callback<ResponseUser1s>() {
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

    public MutableLiveData<String> UploadImage(MultipartBody.Part path){
        MutableLiveData<String>  newsData = new MutableLiveData<>();
        apiImage = RetrofitService.cteateService(APIimage.class);
        apiImage.uploadFile(path).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                newsData.setValue("");
            }
        });
        return newsData;
    }

    public MutableLiveData<ResponseUser1s> RegisterFacebook(String idfb,String fbname,String imgfb,String address,String phone){
        MutableLiveData<ResponseUser1s>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(ApiUser.class);
        dataClient.RegisterFacebook(idfb, fbname, imgfb, address, phone).enqueue(new Callback<ResponseUser1s>() {
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

    public MutableLiveData<ResponseUser1s> ConnectWithFacebook(int id,String idfb,String fbname,String imgfb,String address,String phone){
        MutableLiveData<ResponseUser1s>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(ApiUser.class);
        dataClient.ConnectWithFacebook(id,idfb, fbname, imgfb, address, phone).enqueue(new Callback<ResponseUser1s>() {
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
