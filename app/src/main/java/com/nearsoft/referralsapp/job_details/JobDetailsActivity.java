package com.nearsoft.referralsapp.job_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.send_referral.ReferralActivity;

import java.util.ArrayList;

public class JobDetailsActivity extends AppCompatActivity {
    private ArrayList<String> mJobDescription = new ArrayList<>();
    private ArrayList<String> mTitle;

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

        FillDescription();
    }

    private void FillDescription() {
        mTitle = new ArrayList<>(getIntent().getCategories());

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
