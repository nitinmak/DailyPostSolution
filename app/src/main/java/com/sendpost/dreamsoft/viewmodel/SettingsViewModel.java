package com.sendpost.dreamsoft.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sendpost.dreamsoft.model.SettingModel;
import com.sendpost.dreamsoft.respository.SettingRespository;

import java.util.List;

public class SettingsViewModel extends ViewModel {

    private SettingRespository respository;

    public SettingsViewModel(){
        respository = new SettingRespository();
    }

    public LiveData<List<SettingModel>> getAllSetting(){
        return respository.getData();
    }

}
