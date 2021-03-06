package com.nearsoft.referralsapp.job_details;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nearsoft.referralsapp.JobDescription;
import com.nearsoft.referralsapp.NearsoftJob;
import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.job_openings.JobListingActivity;
import com.nearsoft.referralsapp.send_referral.ReferralActivity;

import java.util.ArrayList;
import java.util.List;

public class JobDetailsActivity extends AppCompatActivity {

    public static final String CONTACT_NAME = "CONTACT_NAME";
    public static final String CONTACT_EMAIL = "CONTACT_EMAIL";
    public static final String RESUME_URI = "RESUME_URI";
    private static final int RESUME_FILE_CODE = 9002;
    public static final String JOB_ID = "JOB_ID";
    private Uri resumeUri;
    private int jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details_activity);

        JobDetailAdapter adapter = fillDescription();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Button sendButton = findViewById(R.id.refer_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent referralActivityIntent = new Intent(getApplicationContext(), ReferralActivity.class);

                EditText contactNameEditText = findViewById(R.id.name_edit_text);
                EditText contactEmailEditText = findViewById(R.id.email_edit_text);

                if (ableToAddParams(referralActivityIntent, contactNameEditText, contactEmailEditText))
                    startActivity(referralActivityIntent);
            }
        });

        ImageButton buttonResumeFile = findViewById(R.id.attach_file_button);
        buttonResumeFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGetFile = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intentGetFile.setType("application/pdf");
                startActivityForResult(
                        Intent.createChooser(intentGetFile, "Select Resume"), RESUME_FILE_CODE);
            }
        });
    }

    private boolean ableToAddParams(Intent referralActivityIntent,
                                    EditText contactNameEditText, EditText contactEmailEditText) {

        if (!isFieldEmtpy(contactNameEditText) && !isFieldEmtpy(contactEmailEditText)) {
            referralActivityIntent.putExtra(CONTACT_NAME, contactNameEditText.getText().toString());
            referralActivityIntent.putExtra(CONTACT_EMAIL, contactEmailEditText.getText().toString());
            referralActivityIntent.putExtra(JOB_ID, jobId);

            if(resumeUri != null)
                referralActivityIntent.putExtra(RESUME_URI, resumeUri.toString());

            return true;
        }

        Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean isFieldEmtpy(EditText contactNameEditText) {
        if (TextUtils.isEmpty(contactNameEditText.getText())) {
            contactNameEditText.setError(getString(R.string.fill_empty_field));
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESUME_FILE_CODE:
                if (resultCode == RESULT_OK) {
                    resumeUri = data.getData();

                    Cursor returnCursor = getContentResolver().query(resumeUri, null,
                            null, null, null);
                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    returnCursor.moveToFirst();

                    TextView resumeTextView = findViewById(R.id.resume_text_view);
                    resumeTextView.setText(returnCursor.getString(nameIndex));

                    ConstraintLayout layout = findViewById(R.id.file_attachment_layout);
                    layout.setVisibility(View.VISIBLE);
                }
        }
    }

    private JobDetailAdapter fillDescription() {
        ArrayList<String> jobDescriptions = new ArrayList<>();
        ArrayList<Integer> title = new ArrayList<>();


        NearsoftJob nearsoftJob =
                (NearsoftJob) getIntent().getSerializableExtra(JobListingActivity.NEARSOFT_JOB);

        jobId = nearsoftJob.getId();
        JobDescription jobDescription = nearsoftJob.getDescription();

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
}
