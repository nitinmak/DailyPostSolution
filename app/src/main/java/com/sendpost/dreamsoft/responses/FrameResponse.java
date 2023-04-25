package com.sendpost.dreamsoft.responses;

import com.google.gson.annotations.SerializedName;
import com.sendpost.dreamsoft.ImageEditor.Stickers.StickerModelCategory;
import com.sendpost.dreamsoft.model.FrameCategoryModel;

import java.util.List;

public class FrameResponse {

    @SerializedName("code")
    public int code;

    @SerializedName("message")
    public String message;

    @SerializedName("framecategories")
    public List<FrameCategoryModel> framecategories;

    @SerializedName("stickercategory")
    public List<StickerModelCategory> stickercategory;

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

    public List<FrameCategoryModel> getFramecategories() {
        return framecategories;
    }

    public void setFramecategories(List<FrameCategoryModel> framecategories) {
        this.framecategories = framecategories;
    }
}
