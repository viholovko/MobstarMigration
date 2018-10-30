package com.viholovko.model.migration;

public class Post {
    private int id;
    private int user_id;
    private int category_id;
    private int old_id;

    public Post(int id, int user_id, int category_id, int old_id) {
        this.id = id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.old_id = old_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getOld_id() {
        return old_id;
    }

    public void setOld_id(int old_id) {
        this.old_id = old_id;
    }
}
