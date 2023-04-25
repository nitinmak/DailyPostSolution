package com.sendpost.dreamsoft.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sendpost.dreamsoft.responses.FrameResponse;
import com.sendpost.dreamsoft.respository.FrameRespository;

public class FrameViewModel extends ViewModel {

    private FrameRespository respository;

    public FrameViewModel(){
        respository = new FrameRespository();
    }

    public LiveData<FrameResponse> getData(){
        return respository.getData();
    }

    public LiveData<FrameResponse> getStickers(){
        return respository.getStickers();
    }

}
