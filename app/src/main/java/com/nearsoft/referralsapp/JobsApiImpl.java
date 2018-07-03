package com.nearsoft.referralsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JobsApiImpl implements JobsApi {
    private final Context context;
    private final JobsView jobsView;

    public JobsApiImpl(Context context, JobsView jobsView) {
        this.context = context;
        this.jobsView = jobsView;
    }

    @Override
    public JsonArrayRequest getRequest() {
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET, JOB_LIST_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<String> jobs = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject1 = response.getJSONObject(i);
                        jobs.add(jsonObject1.optString("name"));
                    }
                    jobsView.updateJobs(jobs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {
                    String jsonError = new String(networkResponse.data);
                    Toast.makeText(context, jsonError, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return jsonObjectRequest;
    }
}
