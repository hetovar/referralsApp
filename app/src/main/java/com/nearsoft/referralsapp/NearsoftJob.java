package com.nearsoft.referralsapp;

import org.json.JSONObject;

import java.util.ArrayList;

public class NearsoftJob {
    private String title;
    private JSONObject description;

    public NearsoftJob() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JSONObject getDescription() {
        return description;
    }

    public void setDescription(JSONObject description) {
        this.description = description;
    }
}
