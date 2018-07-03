package com.nearsoft.referralsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class JobListing extends AppCompatActivity implements JobsView{
    private RequestQueue mRequestQueue;
    private static final String TAG = "TAG";
    private ArrayList<String> jobs = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listing);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mRequestQueue = Volley.newRequestQueue(this);

        JobsApiImpl jobsApi = new JobsApiImpl(this, this);

        mRequestQueue.add(jobsApi.getRequest());

        recyclerView.setHasFixedSize(true);

        mAdapter = new JobListingAdapter(jobs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }

    @Override
    public void updateJobs(List<String> dataSet) {
        jobs.clear();
        jobs.addAll(dataSet);
        mAdapter.notifyDataSetChanged();
    }
}
