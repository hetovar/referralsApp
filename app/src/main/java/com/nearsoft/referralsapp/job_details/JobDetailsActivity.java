package com.nearsoft.referralsapp.job_details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.job_openings.JobListingAdapter;

import java.util.ArrayList;

public class JobDetailsActivity extends AppCompatActivity {
    private ArrayList<String> mJobDescription = new ArrayList<>();
    private ArrayList<String> mTitle;
//    private ArrayList<String> jobDescription;
//    private ArrayList<String> jobGenerals;
//    private ArrayList<String> jobRespobalities;
//    private ArrayList<String> jobSkills;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private JobDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details_activity);

        FillDescription();

        mAdapter = new JobDetailAdapter(mJobDescription, this, mTitle);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        FillDescription();
    }

    private void FillDescription() {
        mTitle = new ArrayList<String>(getIntent().getExtras().keySet());

        String description = "";

        for(String key : mTitle){
            for(String value : getIntent().getStringArrayListExtra(key)){
                description += "* " + value + "\n";
            }
            mJobDescription.add(description);
            description = "";
        }
    }
}
