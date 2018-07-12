package com.nearsoft.referralsapp.job_openings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

        ImageView share = findViewById(R.id.share_button);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });

        Fresco.initialize(this);
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.share_menu, popup.getMenu());
        popup.show();
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
