package com.example.giaysnaker6789.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.models.product_types;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.network.APIProduct;
import com.example.giaysnaker6789.network.APIProducttype;
import com.example.giaysnaker6789.network.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductTypeViewModel extends ViewModel {
    private APIProducttype dataClient;
    private APIProduct apiProduct;

    public MutableLiveData<List<product_types>> LoadProductType(){
        MutableLiveData<List<product_types>>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(APIProducttype.class);
        dataClient.getallloaisp().enqueue(new Callback<List<product_types>>() {
            @Override
            public void onResponse(Call<List<product_types>> call, Response<List<product_types>> response) {
                newsData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<List<product_types>> call, Throwable t) {

            }
        });
        return newsData;
    }

    public MutableLiveData<ProductBaseResponse> LoadListProductType(int idproducttype,int page){
        MutableLiveData<ProductBaseResponse>  newsData = new MutableLiveData<>();
        apiProduct = RetrofitService.cteateService(APIProduct.class);
        apiProduct.getSPLQ(idproducttype,page).enqueue(new Callback<ProductBaseResponse>() {
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

}
