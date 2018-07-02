package com.nearsoft.referralsapp.send_referral;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.nearsoft.referralsapp.ApiClient;
import com.nearsoft.referralsapp.ApiInterface;
import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.Recruiter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralActivity extends AppCompatActivity implements ReferralAdapter.ReferralAdapterListener{
    private Switch mStrongReferralSwitch;
    private RecyclerView mRecyclerView;
    private Button mSendReferralButton;
    private ReferralAdapter mAdapter;
    private ArrayList<Recruiter> recruiters = new ArrayList<>();
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

    @Override
    protected void onStart() {
        super.onStart();
        getRecruiters();
    }

    private void getRecruiters() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<Recruiter>> call = apiService.getRecruiter();
        call.enqueue(new Callback<ArrayList<Recruiter>>() {
            @Override
            public void onResponse(Call<ArrayList<Recruiter>> call, Response<ArrayList<Recruiter>> response) {
                recruiters.clear();
                recruiters.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Recruiter>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onRowClicked(Recruiter recruiter) {
        // TODO: Get the required data that is going to be send through an email.
    }
}