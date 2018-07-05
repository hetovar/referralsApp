package com.nearsoft.referralsapp.sign_in;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.nearsoft.referralsapp.job_openings.JobListingActivity;
import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.databinding.SignInActivityBinding;

public class SignInActivity extends AppCompatActivity implements SignInActivityContract.SingInView {
    private SignInActivityPresenter mPresenter;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SignInActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.sign_in_activity);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mPresenter = new SignInActivityPresenter(this);
        binding.setPresenter(mPresenter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPreviousSignIn();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            mPresenter.checkGoogleAccount(account);
        } catch (ApiException e) {
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode(), e);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        mPresenter = null;
    }

    @Override
    public void startHomeActivity() {
        startActivity(new Intent(this, JobListingActivity.class));
        finish();
    }

    @Override
    public void startAccountsActivity() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void displaySignInErrorMessage() {
        Toast.makeText(this, R.string.sing_in_failure, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkPreviousSignIn() {
        mPresenter.checkGoogleAccount(GoogleSignIn.getLastSignedInAccount(this));
    }

    @Override
    public void signOut() {
        mGoogleSignInClient.signOut();
    }
}
