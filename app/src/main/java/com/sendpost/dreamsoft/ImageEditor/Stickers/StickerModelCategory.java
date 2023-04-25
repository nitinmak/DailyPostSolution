package com.sendpost.dreamsoft.ImageEditor.Stickers;

import com.sendpost.dreamsoft.model.StickerModel;

import java.util.List;

public class StickerModelCategory {

    public String id, name, status, updated_at, created_at;
    public List<StickerModel> stickers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public List<StickerModel> getStickers() {
        return stickers;
    }

    public void setStickers(List<StickerModel> stickers) {
        this.stickers = stickers;
    }
}
