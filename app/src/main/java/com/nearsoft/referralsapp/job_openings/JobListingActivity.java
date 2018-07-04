package com.nearsoft.referralsapp.job_openings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.nearsoft.referralsapp.ApiClient;
import com.nearsoft.referralsapp.ApiInterface;
import com.nearsoft.referralsapp.JobDescription;
import com.nearsoft.referralsapp.NearsoftJob;
import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.job_details.JobDetailsActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobListingActivity extends AppCompatActivity
        implements JobListingAdapter.JobListingAdapterListener {
    public static final String REQUIREMENTS = "Requirements";
    public static final String
            RESPONSIBILITIES = "Responsibilities";
    public static final String SKILLS = "Skills";
    public static final String GENERALS = "Generals";
    private ArrayList<NearsoftJob> mNearsoftJobs = new ArrayList<>();
    private JobListingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_listing_activity);
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);

        mAdapter = new JobListingAdapter(mNearsoftJobs, this);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getJobs();
    }

    private void getJobs() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<NearsoftJob>> call = apiService.getJob();
        call.enqueue(new Callback<ArrayList<NearsoftJob>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<NearsoftJob>> call, @NonNull Response<ArrayList<NearsoftJob>> response) {
                mNearsoftJobs.clear();
                mNearsoftJobs.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<NearsoftJob>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onRowClicked(JobDescription jobDescription) {
        Intent intent = new Intent(this, JobDetailsActivity.class);
        sendDataToActivity(jobDescription, intent);
        startActivity(intent);
    }

    private void sendDataToActivity(JobDescription jobDescription, Intent intent) {
        putIfNonNull(intent, jobDescription.getRequirements(), REQUIREMENTS);
        putIfNonNull(intent, jobDescription.getResponsibilities(), RESPONSIBILITIES);
        putIfNonNull(intent, jobDescription.getSkills(), SKILLS);
        putIfNonNull(intent, jobDescription.getGenerals(), GENERALS);
    }

    private void putIfNonNull(Intent intent, ArrayList<String> params, String tag) {
        if (params != null)
            intent.putExtra(tag, params);
    }
}