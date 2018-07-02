package com.nearsoft.referralsapp.job_openings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
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
        implements JobListingAdapter.JobListingAdapterListener{
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<NearsoftJob> mNearsoftJobs = new ArrayList<>();
    private JobListingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_listing_activity);
        mRecyclerView = findViewById(R.id.recycler_view);

        //TODO: Implement Dagger dependency injection.

        mAdapter = new JobListingAdapter( mNearsoftJobs, this);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //Initialization of Fresco.
        Fresco.initialize(this);
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
            public void onResponse(Call<ArrayList<NearsoftJob>> call, Response<ArrayList<NearsoftJob>> response) {
                mNearsoftJobs.clear();
                mNearsoftJobs.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<NearsoftJob>> call, Throwable t) {
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

        if(jobDescription.getRequirements() != null)
            intent.putExtra("Requirements", jobDescription.getRequirements());
        if(jobDescription.getResponsibilities() != null)
            intent.putExtra("Responsibilities", jobDescription.getResponsibilities());
        if(jobDescription.getSkills() != null)
            intent.putExtra("Skills", jobDescription.getSkills());
        if(jobDescription.getGenerals() != null)
            intent.putExtra("Generals", jobDescription.getGenerals());
        startActivity(intent);
    }
}
