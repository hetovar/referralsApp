package com.nearsoft.referralsapp;

import java.io.Serializable;
import java.util.List;

public class JobDescription implements Serializable {
    private List<String> requirements;
    private List<String> responsibilities;
    private List<String> skills;
    private List<String> generals;

    public JobDescription() {
    }

    public List<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<String> requirements) {
        this.requirements = requirements;
    }

    public List<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getGenerals() {
        return generals;
    }

    public void setGenerals(List<String> generals) {
        this.generals = generals;
    }
}
