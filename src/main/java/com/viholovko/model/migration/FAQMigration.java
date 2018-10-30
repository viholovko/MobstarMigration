package com.viholovko.model.migration;

import java.sql.Date;

public class FAQMigration {
    private int old_id;
    private String title;
    private String description;
    private String content;
    private int user_id;
    private Date created_at;
    private Date updated_at;


    public FAQMigration(int old_id, String title, String description, String content, int user_id, Date created_at, Date updated_at) {
        this.old_id = old_id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.user_id = user_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getOld_id() {
        return old_id;
    }

    public void setOld_id(int old_id) {
        this.old_id = old_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
