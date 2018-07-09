package com.nearsoft.referralsapp;

import java.io.Serializable;

public class NearsoftJob implements Serializable{
    private int jobId;
    private String title;
    private JobDescription description;

    public NearsoftJob() {

    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
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
