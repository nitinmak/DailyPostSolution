package com.sendpost.dreamsoft.model;

import java.util.List;

public class SectionModel {

    private String id;
    private String name;
    private String language;
    private String orders;
    private String post_using;
    private String keyword;
    private String status;
    private String updated_at;
    private String created_at;
    private String posts_count;

    public String getPosts_count() {
        return posts_count;
    }

    public void setPosts_count(String posts_count) {
        this.posts_count = posts_count;
    }

    private List<PostsModel> posts;

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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getPost_using() {
        return post_using;
    }

    public void setPost_using(String post_using) {
        this.post_using = post_using;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public List<PostsModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsModel> posts) {
        this.posts = posts;
    }
}
