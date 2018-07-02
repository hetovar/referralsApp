package com.nearsoft.referralsapp.job_details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.job_openings.JobListingAdapter;
import com.nearsoft.referralsapp.send_referral.ReferralActivity;

import java.util.ArrayList;

public class JobDetailsActivity extends AppCompatActivity {
    private ArrayList<String> mJobDescription = new ArrayList<>();
    private ArrayList<String> mTitle;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private JobDetailAdapter mAdapter;
    private EditText mName;
    private EditText mEmail;
    private EditText mResume;
    private Button mSend;

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

        mName = findViewById(R.id.name_edit_text);
        mEmail = findViewById(R.id.email_edit_text);
        mSend = findViewById(R.id.refer_button);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReferralActivity.class));
            }
        });

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
