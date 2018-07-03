package com.nearsoft.referralsapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JobsApiImpl implements JobsApi {
    private static final String FIELD_NAME = "name";
    private final Context context;
    private final JobsView jobsView;

    JobsApiImpl(Context context, JobsView jobsView) {
        this.context = context;
        this.jobsView = jobsView;
    }

    @Override
    public JsonArrayRequest getRequest() {
        return new JsonArrayRequest(
                Request.Method.GET, JOB_LIST_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<String> jobs = new LinkedList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject job = response.getJSONObject(i);
                        jobs.add(job.optString(FIELD_NAME));
                    }
                    jobsView.updateJobs(jobs);
                } catch (JSONException e) {
                    Log.e("JsonException", "Could not parse jobs.", e);
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
    }
}
