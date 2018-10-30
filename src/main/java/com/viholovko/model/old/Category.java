package com.viholovko.model.old;

import java.sql.Date;

public class Category {
    private int id;
    private boolean active;
    private String name;
    private String description;
    private boolean coming_soon;
    private String image;
    private Date created_at;
    private Date updated_at;

    public Category(int id, boolean active, String name, String description, boolean coming_soon, String image, Date created_at, Date updated_at) {
        this.id = id;
        this.active = active;
        this.name = name;
        this.description = description;
        this.coming_soon = coming_soon;
        this.image = image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComing_soon() {
        return coming_soon;
    }

    public void setComing_soon(boolean coming_soon) {
        this.coming_soon = coming_soon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
