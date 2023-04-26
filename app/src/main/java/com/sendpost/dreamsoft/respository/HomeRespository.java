package com.sendpost.dreamsoft.respository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sendpost.dreamsoft.Classes.Constants;
import com.sendpost.dreamsoft.network.ApiClient;
import com.sendpost.dreamsoft.network.ApiService;
import com.sendpost.dreamsoft.responses.HomeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRespository {


    private ApiService apiService;
    public HomeRespository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<HomeResponse> getData(){
        MutableLiveData<HomeResponse> data = new MutableLiveData<>();
        apiService.getHomeData(Constants.API_KEY ).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<HomeResponse> getImages(){
        MutableLiveData<HomeResponse> data = new MutableLiveData<>();
        apiService.getImageData(Constants.API_KEY).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<HomeResponse> getDaily(){
        MutableLiveData<HomeResponse> data = new MutableLiveData<>();

        Log.d("sdsfs",Constants.API_KEY);
        Log.d("sdsfs",apiService.getdailyData(Constants.API_KEY).toString());
        apiService.getdailyData(Constants.API_KEY).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
    public LiveData<HomeResponse> getDailynew(){
        MutableLiveData<HomeResponse> data = new MutableLiveData<>();

        Log.d("sdsfs",Constants.API_KEY);
        Log.d("sdsfs",apiService.getdailynewData(Constants.API_KEY).toString());
        apiService.getdailynewData(Constants.API_KEY).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<HomeResponse> getVideoCategory(){
        MutableLiveData<HomeResponse> data = new MutableLiveData<>();
        apiService.getVideoCategories(Constants.API_KEY).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<HomeResponse> cheakPromo(String promo){
        MutableLiveData<HomeResponse> data = new MutableLiveData<>();
        apiService.cheakPromo(Constants.API_KEY,promo).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                Log.d("response___t",""+response.body());
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                data.setValue(null);
                Log.d("response___t","E-> "+t.getMessage());
            }
        });
        return data;
    }

    public LiveData<HomeResponse> getGreetingData(){
        MutableLiveData<HomeResponse> data = new MutableLiveData<>();
        apiService.getGreetingData(Constants.API_KEY).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                Log.d("onResponse___"," -> "+response.body().message);
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Log.d("onResponse___"," E-> "+t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<HomeResponse> getPremiumPostByCategory(String type){
        MutableLiveData<HomeResponse> data = new MutableLiveData<>();
        apiService.getPremiumPostByCategory(Constants.API_KEY,type).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                Log.d("onResponse___"," -> "+response.body().message);
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Log.d("onResponse___"," E-> "+t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<HomeResponse> getSubscriptions(){
        MutableLiveData<HomeResponse> data = new MutableLiveData<>();
        apiService.getSubscriptions(Constants.API_KEY).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                Log.d("onResponse___"," -> "+response.body().message);
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Log.d("onResponse___"," E-> "+t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<HomeResponse> getBusinessCards() {
        MutableLiveData<HomeResponse> data = new MutableLiveData<>();
        apiService.getBusinessCards(Constants.API_KEY).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                Log.d("onResponse___", " -> " + response.body().message);
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Log.d("onResponse___", " E-> " + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<HomeResponse> getCategoriesByPage(String type, String search, int page){
        MutableLiveData<HomeResponse> data = new MutableLiveData<>();
        apiService.getCategoriesByPage(Constants.API_KEY,page,type,search).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<HomeResponse> getPostByPage(String type,String language, String post_type, String item_id, String search, int page){
        MutableLiveData<HomeResponse> data = new MutableLiveData<>();
        apiService.getPostByPage(Constants.API_KEY,page,type,language,post_type,item_id,search).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                Log.d("fhvhfhvfdvfd",response.body().toString());
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
