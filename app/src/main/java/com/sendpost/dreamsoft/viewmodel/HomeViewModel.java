package com.sendpost.dreamsoft.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sendpost.dreamsoft.responses.HomeResponse;
import com.sendpost.dreamsoft.respository.HomeRespository;

public class HomeViewModel extends ViewModel {

    private HomeRespository respository;

    public HomeViewModel() {
        respository = new HomeRespository();
    }

    public LiveData<HomeResponse> getData() {
        return respository.getData();
    }

    public LiveData<HomeResponse> getImages() {
        return respository.getImages();
    }

    public LiveData<HomeResponse> getDailyData() {
        return respository.getDaily();
    }

    public LiveData<HomeResponse> cheakPromo(String promo) {
        return respository.cheakPromo(promo);
    }

    public LiveData<HomeResponse> getVideoCategory() {
        return respository.getVideoCategory();
    }

    public LiveData<HomeResponse> getGreetingData() {
        return respository.getGreetingData();
    }

    public LiveData<HomeResponse> getPremiumPostByCategory(String type) {
        return respository.getPremiumPostByCategory(type);
    }

    public LiveData<HomeResponse> getSubscriptions() {
        return respository.getSubscriptions();
    }

    public LiveData<HomeResponse> getPostByPage(String type,String language,String post_type,String item_id,String search,int page) {
        return respository.getPostByPage(type,language,post_type,item_id,search,page);
    }

    public LiveData<HomeResponse> getCategoriesByPage(String type,String search,int page) {
        return respository.getCategoriesByPage(type,search,page);
    }

    public LiveData<HomeResponse> getBusinessCards() {
        return respository.getBusinessCards();
    }

}
