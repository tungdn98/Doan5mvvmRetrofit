package com.example.giaysnaker6789.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseService {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(String base_url) { //tương tác với sever
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS) //ngắt kết nối sau 10s nếu sever không trả dữ liệu về
                .writeTimeout(10000, TimeUnit.MILLISECONDS) //
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .addInterceptor(logging)
                .retryOnConnectionFailure(true) //cố gắng kết nối lại nếu bị trục trặc về mạng
                .protocols(Collections.singletonList(Protocol.HTTP_1_1)) //tránh bị lỗi khi kết nối không được với sever
                .build();
        Gson gson = new GsonBuilder().setLenient().create(); //convert dữ liệu trả về từ sever thành các biến trong java

        //cấu hình convert dữ liệu từ sever trả về
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(base_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }
}
