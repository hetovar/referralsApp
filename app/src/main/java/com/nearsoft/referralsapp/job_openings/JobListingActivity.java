package com.nearsoft.referralsapp.job_openings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.nearsoft.referralsapp.ApiClient;
import com.nearsoft.referralsapp.ApiInterface;
import com.nearsoft.referralsapp.NearsoftJob;
import com.nearsoft.referralsapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobListingActivity extends AppCompatActivity implements JobListingAdapter.JobListingAdapterListener {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<NearsoftJob> mNearsoftJobs = new ArrayList<>();
    private JobListingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listing);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //TODO: Implement Dagger dependency injection.
        //TODO: Utilizar Retrofit

        mAdapter = new JobListingAdapter( mNearsoftJobs, this, this);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getInbox();
    }

    private void getInbox() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<NearsoftJob>> call = apiService.getJob();
        call.enqueue(new Callback<ArrayList<NearsoftJob>>() {
            @Override
            public void onResponse(Call<ArrayList<NearsoftJob>> call, Response<ArrayList<NearsoftJob>> response) {

                mNearsoftJobs.clear();

                for (NearsoftJob message : response.body())
                    mNearsoftJobs.add(message);

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
    public void onRowClicked() {

    }
}
