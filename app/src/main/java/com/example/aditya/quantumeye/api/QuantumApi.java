package com.example.aditya.quantumeye.api;

import android.graphics.Canvas;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface QuantumApi {

    @Multipart
    @POST("/api/client/sendimg")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody fileName);

    @POST("/api/client/signin")
    Call<ServerResponse> checkLoginCredentials(@Body LoginCredentials loginCredentials);


}
