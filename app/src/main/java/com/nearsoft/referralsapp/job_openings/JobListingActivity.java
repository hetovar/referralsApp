package com.nearsoft.referralsapp.job_openings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nearsoft.referralsapp.ApiClient;
import com.nearsoft.referralsapp.ApiInterface;
import com.nearsoft.referralsapp.NearsoftJob;
import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.job_details.JobDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobListingActivity extends AppCompatActivity
        implements JobListingAdapter.JobListingAdapterListener {
    public static final String NEARSOFT_JOB = "NEARSOFT_JOB";
    private ArrayList<NearsoftJob> mNearsoftJobs = new ArrayList<>();
    private JobListingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_listing_activity);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        //TODO: Implement Dagger dependency injection.

        mAdapter = new JobListingAdapter(mNearsoftJobs, this);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);


        Fresco.initialize(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getJobs();
    }

    private void getJobs() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<NearsoftJob>> call = apiService.getJob();
        call.enqueue(new Callback<List<NearsoftJob>>() {
            @Override
            public void onResponse(@NonNull Call<List<NearsoftJob>> call,
                                   @NonNull Response<List<NearsoftJob>> response) {
                mNearsoftJobs.clear();
                mNearsoftJobs.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<NearsoftJob>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onRowClicked(NearsoftJob nearsoftJob) {
        Intent intent = new Intent(this, JobDetailsActivity.class);
        intent.putExtra(NEARSOFT_JOB, nearsoftJob);
        startActivity(intent);
    }
}
