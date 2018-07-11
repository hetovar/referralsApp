package com.nearsoft.referralsapp;

import java.io.Serializable;

public class NearsoftJob implements Serializable{
    private int id;
    private String title;
    private JobDescription description;

    public NearsoftJob() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JobDescription getDescription() {
        return description;
    }

    public void setDescription(JobDescription description) {
        this.description = description;
    }

}
