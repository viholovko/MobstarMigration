package com.viholovko.model.migration;

import java.sql.Date;

public class UserSetting {
    int id;
    int user_id;
    boolean messages_notification;
    boolean friend_requests_notification;
    boolean comments_notification;
    boolean likes_notification;
    boolean taggs_notification;
    boolean system_notification;
    boolean block_user_notification;
    boolean posts_notification;
    int system_of_units;
    String language;
    Date created_at;
    Date updated_at;


    public UserSetting(int user_id, Date created_at, Date updated_at) {
        this.user_id = user_id;
        this.messages_notification = true;
        this.friend_requests_notification = true;
        this.comments_notification = true;
        this.likes_notification = true;
        this.taggs_notification = true;
        this.system_notification = true;
        this.block_user_notification = true;
        this.posts_notification = true;
        this.system_of_units = 0;
        this.language = "en";
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public boolean isMessages_notification() {
        return messages_notification;
    }

    public void setMessages_notification(boolean messages_notification) {
        this.messages_notification = messages_notification;
    }

    public boolean isFriend_requests_notification() {
        return friend_requests_notification;
    }

    public void setFriend_requests_notification(boolean friend_requests_notification) {
        this.friend_requests_notification = friend_requests_notification;
    }

    public boolean isComments_notification() {
        return comments_notification;
    }

    public void setComments_notification(boolean comments_notification) {
        this.comments_notification = comments_notification;
    }

    public boolean isLikes_notification() {
        return likes_notification;
    }

    public void setLikes_notification(boolean likes_notification) {
        this.likes_notification = likes_notification;
    }

    public boolean isTaggs_notification() {
        return taggs_notification;
    }

    public void setTaggs_notification(boolean taggs_notification) {
        this.taggs_notification = taggs_notification;
    }

    public boolean isSystem_notification() {
        return system_notification;
    }

    public void setSystem_notification(boolean system_notification) {
        this.system_notification = system_notification;
    }

    public boolean isBlock_user_notification() {
        return block_user_notification;
    }

    public void setBlock_user_notification(boolean block_user_notification) {
        this.block_user_notification = block_user_notification;
    }

    public int getSystem_of_units() {
        return system_of_units;
    }

    public void setSystem_of_units(int system_of_units) {
        this.system_of_units = system_of_units;
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

    public boolean isPosts_notification() {
        return posts_notification;
    }

    public void setPosts_notification(boolean posts_notification) {
        this.posts_notification = posts_notification;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
