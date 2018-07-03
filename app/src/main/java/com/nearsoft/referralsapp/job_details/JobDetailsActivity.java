package com.nearsoft.referralsapp.job_details;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nearsoft.referralsapp.R;

import java.util.ArrayList;
import java.util.Objects;

public class JobDetailsActivity extends AppCompatActivity {
    private ArrayList<String> mJobDescription = new ArrayList<>();
    private ArrayList<String> mTitle;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details_activity);

        FillDescription();

        JobDetailAdapter mAdapter = new JobDetailAdapter(mJobDescription, mTitle);

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        FillDescription();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void FillDescription() {
        mTitle = new ArrayList<>(Objects.requireNonNull(getIntent().getExtras()).keySet());

        StringBuilder description = new StringBuilder();

        for (String key : mTitle) {
            for (String value : getIntent().getStringArrayListExtra(key)) {
                description.append("* ").append(value).append("\n");
            }
            mJobDescription.add(description.toString());
            description = new StringBuilder();
        }
    }
}
