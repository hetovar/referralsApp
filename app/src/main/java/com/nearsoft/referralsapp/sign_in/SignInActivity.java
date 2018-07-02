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

        // Set the dimensions of the sign-in button visual representation of the google sign in view.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
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

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            mPresenter.checkGoogleAccount(account);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
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
    public void displaySignInSuccessMessage() {
        Toast.makeText(this, "Sign in Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displaySignInErrorMessage() {
        Toast.makeText(this, "Not a nearsoftian email", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkPreviousSignIn() {
        mPresenter.checkGoogleAccount(GoogleSignIn.getLastSignedInAccount(this));
    }

    @Override
    public void signOut() {
        Toast.makeText(this, "You were sign out", Toast.LENGTH_SHORT).show();
        mGoogleSignInClient.signOut();
    }
}
