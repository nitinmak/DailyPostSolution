package com.sendpost.dreamsoft.ImageEditor.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sendpost.dreamsoft.model.LanguageModel;
import com.sendpost.dreamsoft.respository.LanguageRespository;

import java.util.List;

public class LanguageViewModel extends ViewModel {

    private LanguageRespository respository;

    public LanguageViewModel(){
        respository = new LanguageRespository();
    }

    public LiveData<List<LanguageModel>> getData(){
        return respository.getData();
    }

}
