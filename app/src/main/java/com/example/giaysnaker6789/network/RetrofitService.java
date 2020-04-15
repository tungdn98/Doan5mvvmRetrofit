package com.example.giaysnaker6789.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
//    public static String basePath="http://192.168.1.26:81/snaker6789/public/";
//public static String basePath="http://192.168.0.103:81/snaker6789/public/";
public static String basePath="http://192.168.2.32:81/snaker6789/public/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(basePath)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static <S> S cteateService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
