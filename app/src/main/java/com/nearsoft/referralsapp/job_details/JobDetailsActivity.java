package com.nearsoft.referralsapp.job_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.nearsoft.referralsapp.JobDescription;
import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.job_openings.JobListingActivity;
import com.nearsoft.referralsapp.send_referral.ReferralActivity;

import java.util.ArrayList;
import java.util.List;

public class JobDetailsActivity extends AppCompatActivity {
    private ArrayList<String> mJobDescription = new ArrayList<>();
    private ArrayList<Integer> mTitle = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details_activity);

        FillDescription();

        JobDetailAdapter adapter = new JobDetailAdapter(mJobDescription, mTitle);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Button sendButton = findViewById(R.id.refer_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReferralActivity.class));
            }
        });
    }

    private void FillDescription() {
        JobDescription jobDescription =
                (JobDescription) getIntent().getSerializableExtra(JobListingActivity.JOBDESCRIPTION);

        if (jobDescription.getGenerals() != null) {
            mTitle.add(R.string.generals);
            mJobDescription.add(getFormattedString(jobDescription.getGenerals()));
        }
        if (jobDescription.getResponsibilities() != null) {
            mTitle.add(R.string.responsibilities);
            mJobDescription.add(getFormattedString(jobDescription.getResponsibilities()));
        }
        if (jobDescription.getRequirements() != null) {
            mTitle.add(R.string.requirements);
            mJobDescription.add(getFormattedString(jobDescription.getRequirements()));
        }
        if (jobDescription.getSkills() != null) {
            mTitle.add(R.string.skills);
            mJobDescription.add(getFormattedString(jobDescription.getSkills()));
        }
    }

    private String getFormattedString(List<String> requirements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String requirement : requirements) {
            stringBuilder.append("* ").append(requirement).append("\n");
        }

        return stringBuilder.toString();
    }
}
