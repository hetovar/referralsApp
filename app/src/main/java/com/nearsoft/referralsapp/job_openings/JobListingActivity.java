package com.nearsoft.referralsapp.job_openings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nearsoft.referralsapp.R;

import java.util.ArrayList;

public class JobListingActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private static final String TAG = "TAG";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listing);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //TODO: Implement Dagger dependency injection.
        //TODO: Utilizar Retrofit

        // Instantiate the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this);
        String url ="https://my-json-server.typicode.com/haydeeR/API-test/positions";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<String> result = new ArrayList<String>();

                        for(String line : response.split("\n"))
                            if(line.contains("name")) {
                                line = line.replace("\"name\": ", "");
                                result.add(line.replace("\"", "").
                                replace(",", "").trim());
                            }
                        // specify an adapter (see also next example)
                        mAdapter = new JobListingAdapter(result);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "Something went wrong");
            }
        });

        stringRequest.setTag(TAG);
        // Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }
}
