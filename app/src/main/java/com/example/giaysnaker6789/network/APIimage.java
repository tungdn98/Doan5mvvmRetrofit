package com.example.giaysnaker6789.network;
import com.example.giaysnaker6789.models.image_products;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
public interface APIimage {
    @FormUrlEncoded
    @POST("api/getproImage")
    Call<List<image_products>> getproImage(@Field("id") int id);

    @Multipart
    @POST("api/uploadImage")
    Call<String> uploadFile(@Part MultipartBody.Part image);
}
