package com.nearsoft.referralsapp.job_openings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.nearsoft.referralsapp.R;

import java.util.ArrayList;

public class JobListingActivity extends AppCompatActivity {

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
    }
}
