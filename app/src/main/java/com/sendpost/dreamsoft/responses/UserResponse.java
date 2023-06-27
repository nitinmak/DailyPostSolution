package com.sendpost.dreamsoft.responses;

import com.google.gson.annotations.SerializedName;
import com.sendpost.dreamsoft.model.BussinessModel;
import com.sendpost.dreamsoft.model.UserFrameModel;
import com.sendpost.dreamsoft.model.UserModel;
import com.sendpost.dreamsoft.model.UserPostModel;

import java.util.List;

public class UserResponse {

    @SerializedName("code")
    public int code;

    @SerializedName("message")
    public String message;

    @SerializedName("available_pins")
    public String available_pins;

    @SerializedName("userslist")
    public List<UserModel> userslist;

    public List<UserModel> getActuserslist() {
        return actuserslist;
    }

    public void setActuserslist(List<UserModel> actuserslist) {
        this.actuserslist = actuserslist;
    }

    @SerializedName("actuserslist")
    public List<UserModel> actuserslist;

    public List<UserModel> getUserslist() {
        return userslist;
    }

    public void setUserslist(List<UserModel> userslist) {
        this.userslist = userslist;
    }


    public String getAvailable_points() {
        return available_points;
    }

    public void setAvailable_points(String available_points) {
        this.available_points = available_points;
    }

    @SerializedName("available_points")
    public String available_points;

    @SerializedName("used_pins")
    public String used_pins;

    public String getAvailable_pins() {
        return available_pins;
    }

    public void setAvailable_pins(String available_pins) {
        this.available_pins = available_pins;
    }

    public String getUsed_pins() {
        return used_pins;
    }

    public void setUsed_pins(String used_pins) {
        this.used_pins = used_pins;
    }

    public String getPurchased_pins() {
        return purchased_pins;
    }

    public void setPurchased_pins(String purchased_pins) {
        this.purchased_pins = purchased_pins;
    }

    @SerializedName("purchased_pins")
    public String purchased_pins;

    @SerializedName("used_point")
    public String used_point;

    public String getUsed_point() {
        return used_point;
    }

    public void setUsed_point(String used_point) {
        this.used_point = used_point;
    }

    public String getRemain_point() {
        return remain_point;
    }

    public void setRemain_point(String remain_point) {
        this.remain_point = remain_point;
    }

    @SerializedName("remain_point")
    public String remain_point;

    @SerializedName("user")
    public UserModel userModel;


    public List<ActiveUser> getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(List<ActiveUser> activeUser) {
        this.activeUser = activeUser;
    }

    @SerializedName("data")
    public List<ActiveUser> activeUser;

    public List<PointHistoey> getPointhistory() {
        return pointhistory;
    }


    public void setPointhistory(List<PointHistoey> pointhistory) {
        this.pointhistory = pointhistory;
    }

    @SerializedName("pointdata")
    public List<PointHistoey> pointhistory;


    @SerializedName("business")
    public BussinessModel business;

    @SerializedName("businesses")
    public List<BussinessModel> businesses;

    @SerializedName("userframes")
    public List<UserFrameModel> userframes;

    @SerializedName("userposts")
    public List<UserPostModel> userposts;

    public List<BussinessModel> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<BussinessModel> businesses) {
        this.businesses = businesses;
    }

    public BussinessModel getBusiness() {
        return business;
    }

    public void setBusiness(BussinessModel business) {
        this.business = business;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<UserFrameModel> getUserframes() {
        return userframes;
    }

    public void setUserframes(List<UserFrameModel> userframes) {
        this.userframes = userframes;
    }

    public List<UserPostModel> getUserposts() {
        return userposts;
    }

    public void setUserposts(List<UserPostModel> userposts) {
        this.userposts = userposts;
    }
}
