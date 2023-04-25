package com.sendpost.dreamsoft.respository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sendpost.dreamsoft.Classes.Constants;
import com.sendpost.dreamsoft.network.ApiClient;
import com.sendpost.dreamsoft.network.ApiService;
import com.sendpost.dreamsoft.responses.FrameResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FrameRespository {


    private ApiService apiService;
    public FrameRespository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<FrameResponse> getData(){
        MutableLiveData<FrameResponse> data = new MutableLiveData<>();
        apiService.getFrames(Constants.API_KEY).enqueue(new Callback<FrameResponse>() {
            @Override
            public void onResponse(Call<FrameResponse> call, Response<FrameResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<FrameResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<FrameResponse> getStickers(){
        MutableLiveData<FrameResponse> data = new MutableLiveData<>();
        apiService.getStickersByCategory(Constants.API_KEY).enqueue(new Callback<FrameResponse>() {
            @Override
            public void onResponse(Call<FrameResponse> call, Response<FrameResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<FrameResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
