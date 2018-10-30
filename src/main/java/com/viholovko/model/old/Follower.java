package com.viholovko.model.old;

import java.sql.Date;

public class Follower {
    private int followerId;
    private int star;
    private Date created_at;
    private Date updated_at;

    public Follower(int followerId, int star, Date created_at, Date updated_at) {
        this.followerId = followerId;
        this.star = star;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
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
