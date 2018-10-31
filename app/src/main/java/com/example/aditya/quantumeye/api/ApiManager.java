package com.example.aditya.quantumeye.api;


import com.example.aditya.quantumeye.utils.QuantumUtils;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static QuantumApi mService;
    private static ApiManager mApiManager;

    private ApiManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(QuantumUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(QuantumApi.class);
    }

    public static ApiManager getInstance(){
        if(mApiManager == null){
            mApiManager = new ApiManager();
        }
        return mApiManager;
    }

    public void uploadImageToServer(MultipartBody.Part capturedImage, RequestBody fileName, Callback<ServerResponse> callback){
        Call<ServerResponse> imageCall = mService.uploadFile(capturedImage, fileName);
        imageCall.enqueue(callback);
    }

    public void checkLoginCredentials(LoginCredentials loginCredentials, Callback<ServerResponse> callback){
        Call<ServerResponse> serverResponseCall = mService.checkLoginCredentials(loginCredentials);
        serverResponseCall.enqueue(callback);
    }

}
