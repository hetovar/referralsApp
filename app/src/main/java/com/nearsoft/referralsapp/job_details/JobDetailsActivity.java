package com.nearsoft.referralsapp.job_details;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nearsoft.referralsapp.R;

import java.util.ArrayList;

public class JobDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details_activity);

        JobDetailAdapter detailAdapter = fillDescription();

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(detailAdapter);
    }

    private JobDetailAdapter fillDescription() {
        ArrayList<String> title = new ArrayList<>((getIntent().getExtras()).keySet());
        ArrayList<String> jobDescriptions = new ArrayList<>();

        for (String key : title) {
            StringBuilder description = new StringBuilder();
            for (String value : getIntent().getStringArrayListExtra(key)) {
                description.append("* ").append(value).append(System.getProperty("line.separator"));
            }
            jobDescriptions.add(description.toString());
        }
        return new JobDetailAdapter(jobDescriptions, title);
    }
}