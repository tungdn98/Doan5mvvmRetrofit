package com.example.giaysnaker6789.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaysnaker6789.models.vouchers;
import com.example.giaysnaker6789.network.APIProduct;
import com.example.giaysnaker6789.network.ApiVoucher;
import com.example.giaysnaker6789.network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherViewModel extends ViewModel {
    private ApiVoucher dataClient;

    public MutableLiveData<vouchers> checkVoucher(String voucher ,int idproduct){
        MutableLiveData<vouchers>  newsData = new MutableLiveData<>();
        dataClient = RetrofitService.cteateService(ApiVoucher.class);
        dataClient.checkVoucher(voucher,idproduct).enqueue(new Callback<vouchers>() {
            @Override
            public void onResponse(Call<vouchers> call, Response<vouchers> response) {
                if (response.isSuccessful()) {
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<vouchers> call, Throwable t) {

            }
        });
        return newsData;
    }
}
