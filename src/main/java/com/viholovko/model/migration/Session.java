package com.viholovko.model.migration;

import java.sql.Date;

public class Session {
    private String token;
    private int user_id;
    private Date created;
    private Date updated;

    public Session(String token, int user_id, Date created, Date updated) {
        this.token = token;
        this.user_id = user_id;
        this.created = created;
        this.updated = updated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
