package com.nearsoft.referralsapp;

import java.util.ArrayList;

public class JobDescription {
    private ArrayList<String> requirements;
    private ArrayList<String> responsibilities;
    private ArrayList<String> skills;
    private ArrayList<String> generals;

    public JobDescription() {
    }

    public ArrayList<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(ArrayList<String> requirements) {
        this.requirements = requirements;
    }

    public ArrayList<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(ArrayList<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public ArrayList<String> getGenerals() {
        return generals;
    }

    public void setGenerals(ArrayList<String> generals) {
        this.generals = generals;
    }
}
