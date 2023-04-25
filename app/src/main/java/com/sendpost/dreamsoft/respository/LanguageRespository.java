package com.sendpost.dreamsoft.respository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sendpost.dreamsoft.Classes.Constants;
import com.sendpost.dreamsoft.model.LanguageModel;
import com.sendpost.dreamsoft.network.ApiClient;
import com.sendpost.dreamsoft.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LanguageRespository {


    private ApiService apiService;
    public LanguageRespository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<List<LanguageModel>> getData(){
        MutableLiveData<List<LanguageModel>> data = new MutableLiveData<>();
        apiService.getLanguages(Constants.API_KEY).enqueue(new Callback<List<LanguageModel>>() {
            @Override
            public void onResponse(Call<List<LanguageModel>> call, Response<List<LanguageModel>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<LanguageModel>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
