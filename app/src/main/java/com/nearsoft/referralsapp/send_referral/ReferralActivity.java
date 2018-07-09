package com.nearsoft.referralsapp.send_referral;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.nearsoft.referralsapp.ApiClient;
import com.nearsoft.referralsapp.ApiInterface;
import com.nearsoft.referralsapp.Mail;
import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.Recruiter;
import com.nearsoft.referralsapp.job_details.JobDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralActivity extends AppCompatActivity implements ReferralAdapter.ReferralAdapterListener {
    private ReferralAdapter mAdapter;
    private ArrayList<Recruiter> recruiters = new ArrayList<>();
    private Switch switchStrongReferral;
    private Button sendEmail;
    private Recruiter mRecruiter;
    private String referName;
    private String referEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.referral_activity);

        getReferInformation();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        switchStrongReferral = findViewById(R.id.switch_strong_referral);
        final EditText editTextWhen = findViewById(R.id.when_edit_text);
        final EditText editTextWhere = findViewById(R.id.where_edit_text);
        final EditText editTextWhy = findViewById(R.id.why_edit_text);

        mAdapter = new ReferralAdapter(recruiters, this);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        switchStrongReferral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    editTextWhen.setVisibility(View.VISIBLE);
                    editTextWhere.setVisibility(View.VISIBLE);
                    editTextWhy.setVisibility(View.VISIBLE);
                } else {
                    editTextWhen.setVisibility(View.INVISIBLE);
                    editTextWhere.setVisibility(View.INVISIBLE);
                    editTextWhy.setVisibility(View.INVISIBLE);
                }
            }
        });

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Are you sure to send the email with this contact?");
        dialogBuilder.setCancelable(true);

        dialogBuilder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Mail mail = new Mail();
                        mail.setJobId(1);
                        mail.setRecruiterId(mRecruiter.getId());
                        mail.setReferredName(referName);
                        mail.setReferredEmail(referEmail);

                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                        apiService.sendMail(mRecruiter.getId(),
                                1, referName, referEmail, null).enqueue(new Callback<Mail>() {
                            @Override
                            public void onResponse(Call<Mail> call, Response<Mail> response) {
                                if(response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "email send successfully", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Mail> call, Throwable t) {
                            }
                        });

                        dialog.cancel();
                    }
                });

        dialogBuilder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        sendEmail = findViewById(R.id.send_referral_button);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }

        });
    }

    private void getReferInformation() {
        referName = getIntent().getStringExtra(JobDetailsActivity.CONTACT_NAME);
        referEmail = getIntent().getStringExtra(JobDetailsActivity.CONTACT_EMAIL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getRecruiters();
    }

    private void getRecruiters() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Recruiter>> call = apiService.getRecruiter();
        call.enqueue(new Callback<List<Recruiter>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recruiter>> call,
                                   @NonNull Response<List<Recruiter>> response) {
                recruiters.clear();
                recruiters.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Recruiter>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext()
                        , "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onRowClicked(Recruiter recruiter) {
        if (switchStrongReferral.getVisibility() == View.INVISIBLE
                || sendEmail.getVisibility() == View.INVISIBLE) {
            switchStrongReferral.setVisibility(View.VISIBLE);
            sendEmail.setVisibility(View.VISIBLE);
        }
        mRecruiter = recruiter;
    }
}