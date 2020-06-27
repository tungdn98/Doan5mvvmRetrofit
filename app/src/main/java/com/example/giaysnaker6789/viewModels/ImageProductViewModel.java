package com.example.giaysnaker6789.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.models.image_products;
import com.example.giaysnaker6789.network.APIProduct;
import com.example.giaysnaker6789.network.APIimage;
import com.example.giaysnaker6789.network.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageProductViewModel extends ViewModel {
    private APIimage dataClient;

    public MutableLiveData<List<image_products>> getproImage(int id){
        MutableLiveData<List<image_products>>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIimage.class);
        dataClient.getproImage(id).enqueue(new Callback<List<image_products>>() {
            @Override
            public void onResponse(Call<List<image_products>> call, Response<List<image_products>> response) {
                if(response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<image_products>> call, Throwable t) {

            }
        });
        return newsData;
    }

}
