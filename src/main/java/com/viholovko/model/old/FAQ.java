package com.viholovko.model.old;

import java.sql.Date;

public class FAQ {
    private int id;
    private String question;
    private String answer;
    private Date created_at;
    private Date updated_at;

    public FAQ(int id, String question, String answer, Date created_at, Date updated_at) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
