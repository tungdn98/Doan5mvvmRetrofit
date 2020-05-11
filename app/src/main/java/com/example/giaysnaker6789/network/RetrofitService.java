package com.example.giaysnaker6789.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
   //public static String basePath="http://192.168.2.32:81/snaker6789/public/";
        public static String basePath="http://c2e99730.ngrok.io/";
    //public static String basePath="http://192.168.0.102:81/snaker6789/public/";
   // public static String basePath="http://eb1e1640.ngrok.io/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(basePath)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static <S> S cteateService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
