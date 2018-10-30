package com.viholovko.model.migration;

import java.sql.Date;

public class CategoryMigration {
    private int id;
    private int old_id;
    private String title;
    private String gradient_start;
    private String gradient_end;
    private Date created_at;
    private Date updated_at;

    public CategoryMigration(int old_id, String title, String gradient_start, String gradient_end, Date created_at, Date updated_at) {
        this.old_id = old_id;
        this.title = title;
        this.gradient_start = gradient_start;
        this.gradient_end = gradient_end;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public CategoryMigration(int id, Date created_at, Date updated_at) {
        this.id = id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public CategoryMigration(int id, int old_id) {
        this.id = id;
        this.old_id = old_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getGradient_start() {
        return gradient_start;
    }

    public void setGradient_start(String gradient_start) {
        this.gradient_start = gradient_start;
    }

    public String getGradient_end() {
        return gradient_end;
    }

    public void setGradient_end(String gradient_end) {
        this.gradient_end = gradient_end;
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
