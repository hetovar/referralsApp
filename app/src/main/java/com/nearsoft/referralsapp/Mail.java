package com.nearsoft.referralsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public class Mail {
    @SerializedName("recruiter_id")
    @Expose
    private long recruiterId;
    @SerializedName("job_id")
    @Expose
    private long jobId;
    @SerializedName("referred_name")
    @Expose
    private String referredName;
    @SerializedName("referred_email")
    @Expose
    private String referredEmail;
    @SerializedName("resume_file")
    @Expose
    private File resume;

    public Mail() {
    }

    public long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(long recruiterId) {
        this.recruiterId = recruiterId;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getReferredName() {
        return referredName;
    }

    public void setReferredName(String referredName) {
        this.referredName = referredName;
    }

    public String getReferredEmail() {
        return referredEmail;
    }

    public void setReferredEmail(String referredEmail) {
        this.referredEmail = referredEmail;
    }

    public File getResume() {
        return resume;
    }

    public void setResume(File resume) {
        this.resume = resume;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "recruiterId=" + recruiterId +
                ", jobId=" + jobId +
                ", referredName='" + referredName + '\'' +
                ", referredEmail='" + referredEmail + '\'' +
                ", resume=" + resume +
                '}';
    }
}
