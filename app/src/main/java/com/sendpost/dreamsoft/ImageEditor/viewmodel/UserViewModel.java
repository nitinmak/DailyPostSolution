package com.sendpost.dreamsoft.ImageEditor.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sendpost.dreamsoft.responses.UserResponse;
import com.sendpost.dreamsoft.respository.UserRespository;

import org.json.JSONException;
import org.json.JSONObject;

public class UserViewModel extends ViewModel {

    private UserRespository respository;

    public UserViewModel() {
        respository = new UserRespository();
    }

    public LiveData<UserResponse> getUserProfile(String uid) {
        return respository.getProfileData(uid);
    }

    public LiveData<UserResponse> updateProfilePic(String uid, String path) {
        return respository.updateProfilePic(uid, path);
    }

    public LiveData<UserResponse> getUserPosts(String uid) {
        return respository.getUserPosts(uid);
    }

    public LiveData<UserResponse> uploadUserPost(String uid, String path) {
        return respository.uploadUserPost(uid, path);
    }

    public LiveData<UserResponse> updateProfile(String uid, String name, String email, String number, String address, String website, String parent_id) {
        return respository.updateProfile(uid, name, email, number,address,website,parent_id);
    }

    public LiveData<UserResponse> addContact(String uid, String number, String message) {
        return respository.addContact(uid, number, message);
    }

    public LiveData<UserResponse> activeuser(String uid, String number, String reqwallete) {
        return respository.activeuser(uid, number,reqwallete);
    }

    public LiveData<UserResponse> getUserBusiness(String uid) {
        return respository.getUserBusiness(uid);
    }

    public LiveData<UserResponse> getBusinessDetail(String uid, String id) {
        return respository.getBusinessDetail(uid, id);
    }

    public LiveData<UserResponse> addUserBussiness(JSONObject jsonObject) {
        try {
            return respository.addUserBussiness(jsonObject);
        } catch (JSONException e) {
            return null;
        }
    }

    public LiveData<UserResponse> updateUserSubscription(String uid, String type, String sid, String tid,String promocode) {
        return respository.updateSuscription(uid, type, sid, tid,promocode);
    }

    public LiveData<UserResponse> getUserFrames(String uid) {
        return respository.getUserFrames(uid);
    }

    public LiveData<UserResponse> getInvitedUser(String uid) {
        return respository.getInvitedUser(uid);
    }

    public LiveData<UserResponse> login(JSONObject object) {
        try {
            return respository.login(object);
        } catch (JSONException e) {
            return null;
        }
    }

    public LiveData<UserResponse> activeuserhistory(String uid) {
        return respository.getActiveUser(uid);
    }

    public LiveData<UserResponse> getpinpointhistory(String uid) {
        return respository.getpinpoint(uid);
    }
    public LiveData<UserResponse> getpointhistory(String uid) {
        return respository.getpointhistory(uid);
    }

}





