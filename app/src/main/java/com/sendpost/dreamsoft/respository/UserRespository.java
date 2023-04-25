package com.sendpost.dreamsoft.respository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sendpost.dreamsoft.Classes.Constants;
import com.sendpost.dreamsoft.network.ApiClient;
import com.sendpost.dreamsoft.network.ApiService;
import com.sendpost.dreamsoft.responses.UserResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRespository {

    private ApiService apiService;

    public UserRespository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<UserResponse> getProfileData(String uid) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.geUserProfile(Constants.API_KEY, uid).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.d("onResponse___", uid + " -> " + response.body());
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("onResponse___", uid + " E-> " + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<UserResponse> getUserPosts(String uid) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.getUserPosts(Constants.API_KEY, uid).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.d("onResponse___", uid + " -> " + response.body());
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("onResponse___", uid + " E-> " + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<UserResponse> updateProfilePic(String uid, String path) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), new File(path));
        MultipartBody.Part imageBody =
                MultipartBody.Part.createFormData("image", new File(path).getName(), requestFile);
        RequestBody userid =
                RequestBody.create(MediaType.parse("multipart/form-data"), uid);

        apiService.updateProfilePic(Constants.API_KEY, userid, imageBody).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<UserResponse> uploadUserPost(String uid, String path) {

        MutableLiveData<UserResponse> data = new MutableLiveData<>();

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), new File(path));

        MultipartBody.Part fileBody = null;
        if (path.endsWith(".mp4")) {
            fileBody = MultipartBody.Part.createFormData("video", new File(path).getName(), requestFile);
        } else {
            fileBody = MultipartBody.Part.createFormData("image", new File(path).getName(), requestFile);
        }

        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), uid);

        apiService.uploadUserPost(Constants.API_KEY, userid, fileBody).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<UserResponse> login(JSONObject object) throws JSONException {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();

        apiService.login(
                Constants.API_KEY,
                "" + object.getString("social"),
                "" + object.getString("social_id"),
                "" + object.getString("auth_token"),
                "" + object.getString("email"),
                "" + object.getString("number"),
                "" + object.getString("profile_pic"),
                "" + object.getString("name"),
                "" + object.getString("device_token")).enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<UserResponse> updateProfile(String uid, String name, String email, String number, String address, String website, String parent_id) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.updateProfile(
                Constants.API_KEY,
                "" + uid,
                "" + name,
                "" + email,
                "" + number,
                ""+address,
                ""+website,
                ""+parent_id
                ).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<UserResponse> addContact(String uid, String number, String message) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.addContact(
                Constants.API_KEY,
                "" + uid,
                "" + number,
                "" + message).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<UserResponse> activeuser(String uid, String number, String reqwallete) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.activeuser(
                Constants.API_KEY,
                "" + uid,
                "" + number,
                ""+reqwallete
               ).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<UserResponse> getBusinessDetail(String uid, String bid) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.getBusinessDetail(
                Constants.API_KEY,
                "" + uid,
                "" + bid).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<UserResponse> updateSuscription(String uid, String type, String sid, String tid,String promocode) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.updateSubscription(
                Constants.API_KEY,
                "" + uid,
                "" + type,
                "" + sid,
                "" + tid,
                ""+promocode).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<UserResponse> getUserFrames(String uid) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.getUserFrames(
                Constants.API_KEY,
                "" + uid).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<UserResponse> getInvitedUser(String uid) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.getInvitedUser(
                Constants.API_KEY,
                "" + uid).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<UserResponse> getUserBusiness(String uid) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.getUserBusiness(
                Constants.API_KEY,
                "" + uid).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<UserResponse> getActiveUser(String uid) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.getActiveuser(
                Constants.API_KEY,
                "" + uid).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<UserResponse> getpinpoint(String uid) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.getpinpoint(
                Constants.API_KEY,
                "" + uid).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<UserResponse> getpointhistory(String uid) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.getPointhistory(
                Constants.API_KEY,
                "" + uid).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<UserResponse> addUserBussiness(JSONObject jsonObject) throws JSONException {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();

        MultipartBody.Part fileBody = null;
        if (!jsonObject.getString("image").isEmpty()){
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), new File(jsonObject.getString("image")));
            fileBody = MultipartBody.Part.createFormData("image", new File(jsonObject.getString("image")).getName(), requestFile);
        }
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.getString("id"));
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.getString("user_id"));
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.getString("name"));
        RequestBody number = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.getString("number"));
        RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.getString("email"));
        RequestBody website = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.getString("website"));
        RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.getString("address"));
        RequestBody whatsapp = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.getString("whatsapp"));
        RequestBody facebook = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.getString("facebook"));
        RequestBody youtube = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.getString("youtube"));
        RequestBody instagram = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.getString("instagram"));
        RequestBody about = RequestBody.create(MediaType.parse("multipart/form-data"), jsonObject.getString("about"));
        Log.d("RequestBody___",jsonObject.getString("address"));
        apiService.addBusiness(
                Constants.API_KEY,
                fileBody,
                id,
                userid,
                name,
                number,
                email,
                website,
                address,
                whatsapp,
                facebook,
                youtube,
                instagram,
                about).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


}
