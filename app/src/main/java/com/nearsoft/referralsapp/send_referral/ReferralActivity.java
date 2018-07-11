package com.nearsoft.referralsapp.send_referral;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.util.IOUtils;
import com.nearsoft.referralsapp.ApiClient;
import com.nearsoft.referralsapp.ApiInterface;
import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.Recruiter;
import com.nearsoft.referralsapp.job_details.JobDetailsActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralActivity extends AppCompatActivity implements ReferralAdapter.ReferralAdapterListener {
    public static final String RESUME_FILE = "resume_file";
    public static final String UPLOAD_RESUME_PDF = "uploadResume.pdf";
    private ReferralAdapter mAdapter;
    private ArrayList<Recruiter> recruiters = new ArrayList<>();
    private Switch switchStrongReferral;
    private Button sendEmail;
    private Recruiter mRecruiter;
    private String referName;
    private String referEmail;
    private int jobId;
    private Uri resumeUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.referral_activity);

        getReferInformation();

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
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
        dialogBuilder.setMessage(R.string.question_send_mail);
        dialogBuilder.setCancelable(true);

        dialogBuilder.setPositiveButton(
                R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                        MultipartBody.Part body = null;
                        File referResume = getFile();

                        if (referResume != null) {
                            body = transformFileIntoPart(referResume);
                        }

                        RequestBody name = RequestBody
                                .create(okhttp3.MultipartBody.FORM, referName);

                        RequestBody email = RequestBody
                                .create(okhttp3.MultipartBody.FORM, referEmail);

                        apiService.sendMail(mRecruiter.getId(),
                                jobId, name, email, body).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), R.string.email_send_successfully, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        dialog.cancel();
                    }
                });

        dialogBuilder.setNegativeButton(
                R.string.no,
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

    @NonNull
    private MultipartBody.Part transformFileIntoPart(File referResume) {
        MultipartBody.Part body;
        RequestBody requestFile = RequestBody
                .create(MediaType.parse(getContentResolver().getType(resumeUri)),
                        referResume);

        body = MultipartBody.Part.createFormData(RESUME_FILE,
                referResume.getName(), requestFile);

        return body;
    }

    private File getFile() {
        File referResume = null;
        try (InputStream inputStream = getContentResolver().openInputStream(resumeUri)) {
            if (inputStream != null) {
                referResume = new File(getCacheDir(), UPLOAD_RESUME_PDF);
                try (FileOutputStream outputStream = new FileOutputStream(referResume)) {
                    IOUtils.copyStream(inputStream, outputStream);
                }
            } else {
                Log.w("REFERRAL_ACTIVITY", "Could not open input stream for " + resumeUri);
            }
        } catch (IOException e) {
            Log.e("REFERRAL_ACTIVITY", "Input output error", e);
        }

        return referResume;
    }

    private void getReferInformation() {
        referName = getIntent().getStringExtra(JobDetailsActivity.CONTACT_NAME);
        referEmail = getIntent().getStringExtra(JobDetailsActivity.CONTACT_EMAIL);
        jobId = getIntent().getIntExtra(JobDetailsActivity.JOB_ID, -1);
        String resumePath = getIntent().getStringExtra(JobDetailsActivity.RESUME_URI);

        if (resumePath != null && !resumePath.isEmpty())
            resumeUri = Uri.parse(resumePath);
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