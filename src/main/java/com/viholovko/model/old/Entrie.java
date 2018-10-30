package com.viholovko.model.old;

import java.sql.Date;

public class Entrie {
    private int id;
    private int user_id;
    private int category_id;
    private String name;
    private String type;
    private String description;
    private boolean status;
    private boolean isPublic;
    private boolean onYoutube;
    private String thumbnail;
    private String language;
    private String source;
    private Date created_at;
    private Date updated_at;
    private boolean useThumb;

    public Entrie(int id, int user_id, int category_id, String name, String type, String description, boolean status, boolean isPublic, boolean onYoutube, String thumbnail, String language, String source, Date created_at, Date updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.status = status;
        this.isPublic = isPublic;
        this.onYoutube = onYoutube;
        this.thumbnail = thumbnail;
        this.language = language;
        this.source = source;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public boolean isUseThumb() {
        return useThumb;
    }

    public void setUseThumb(boolean useThumb) {
        this.useThumb = useThumb;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isOnYoutube() {
        return onYoutube;
    }

    public void setOnYoutube(boolean onYoutube) {
        this.onYoutube = onYoutube;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
