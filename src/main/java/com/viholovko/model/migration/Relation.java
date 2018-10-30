package com.viholovko.model.migration;

import java.sql.Date;

public class Relation {
    int follower_id;
    int followed_id;
    boolean confirmed;
    Date created_at;
    Date updated_at;

    public Relation(int follower_id, int followed_id, boolean confirmed, Date created_at, Date updated_at) {
        this.follower_id = follower_id;
        this.followed_id = followed_id;
        this.confirmed = confirmed;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(int follower_id) {
        this.follower_id = follower_id;
    }

    public int getFollowed_id() {
        return followed_id;
    }

    public void setFollowed_id(int followed_id) {
        this.followed_id = followed_id;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
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
