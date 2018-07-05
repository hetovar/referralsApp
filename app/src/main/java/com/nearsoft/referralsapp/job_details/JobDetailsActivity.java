package com.nearsoft.referralsapp.job_details;

import android.support.v7.app.AppCompatActivity;
package com.nearsoft.referralsapp.job_details;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nearsoft.referralsapp.JobDescription;
import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.job_openings.JobListingActivity;

import java.util.ArrayList;
import java.util.List;

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
        JobDescription jobDescription =
                (JobDescription) getIntent().getSerializableExtra(JobListingActivity.JOBDESCRIPTION);
        ArrayList<Integer> title = new ArrayList<>();
        ArrayList<String> jobDescriptions = new ArrayList<>();

        if (jobDescription.getGenerals() != null) {
            title.add(R.string.generals);
            jobDescriptions.add(getFormattedString(jobDescription.getGenerals()));
        }
        if (jobDescription.getResponsibilities() != null) {
            title.add(R.string.responsibilities);
            jobDescriptions.add(getFormattedString(jobDescription.getResponsibilities()));
        }
        if (jobDescription.getRequirements() != null) {
            title.add(R.string.requirements);
            jobDescriptions.add(getFormattedString(jobDescription.getRequirements()));
        }
        if (jobDescription.getSkills() != null) {
            title.add(R.string.skills);
            jobDescriptions.add(getFormattedString(jobDescription.getSkills()));
        }

        return new JobDetailAdapter(jobDescriptions, title);
    }

    private String getFormattedString(List<String> requirements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String requirement : requirements) {
            stringBuilder.append("* ").append(requirement).append("\n");
        }

        return stringBuilder.toString();
    }