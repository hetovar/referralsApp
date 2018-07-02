package com.nearsoft.referralsapp.send_referral;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.Recruiter;
import com.nearsoft.referralsapp.job_details.JobDetailAdapter;

import java.util.ArrayList;

public class ReferralActivity extends AppCompatActivity implements ReferralAdapter.ReferralAdapterListener{
    private Switch mStrongReferralSwitch;
    private RecyclerView mRecyclerView;
    private Button mSendReferralButton;
    private ReferralAdapter mAdapter;
    private ArrayList<Recruiter> recruiters;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.referral_activity);

        mRecyclerView = findViewById(R.id.recycler_view);
        mStrongReferralSwitch = findViewById(R.id.switch_strong_referral);
        mSendReferralButton = findViewById(R.id.send_referral_button);

        mAdapter = new ReferralAdapter(recruiters, this);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}