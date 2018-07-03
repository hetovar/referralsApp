package com.nearsoft.referralsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class JobApiImpl implements JobsApi {
    private final Context context;

    public JobApiImpl(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public StringRequest jobEntityList() {

        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.GET, API_URL_GET_JOB_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Response: " + response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        return jsonObjectRequest;
    }
}
