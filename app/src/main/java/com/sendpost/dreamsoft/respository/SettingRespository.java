package com.sendpost.dreamsoft.respository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sendpost.dreamsoft.Classes.Constants;
import com.sendpost.dreamsoft.model.SettingModel;
import com.sendpost.dreamsoft.network.ApiClient;
import com.sendpost.dreamsoft.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingRespository {


    private ApiService apiService;
    public SettingRespository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<List<SettingModel>> getData(){

        Log.d("Constants_API_KEY",Constants.API_KEY);

        MutableLiveData<List<SettingModel>> data = new MutableLiveData<>();

        apiService.getSettings(Constants.API_KEY).enqueue(new Callback<List<SettingModel>>() {
            @Override
            public void onResponse(Call<List<SettingModel>> call, Response<List<SettingModel>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<SettingModel>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
