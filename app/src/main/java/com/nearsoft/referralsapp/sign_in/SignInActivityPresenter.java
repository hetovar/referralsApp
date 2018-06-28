package com.nearsoft.referralsapp.sign_in;

import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class SignInActivityPresenter implements SignInActivityContract.SignInPresenter {
    private SignInActivityContract.SingInView mView;
    private static final String EMAIL_REGEX = "^[a-zA-Z]+@nearsoft.com$";

    public SignInActivityPresenter(SignInActivityContract.SingInView mView) {
        this.mView = mView;
    }

    @Override
    public void handleSignInWithGoogleClick(View view) {
        if(view != null)
            mView.startAccountsActivity();
    }

    @Override
    public void signInSuccess() {
        mView.displaySignInSuccessMessage();
        mView.startHomeActivity();
    }

    @Override
    public void signInError() {
        mView.displaySignInErrorMessage();
        mView.signOut();
    }

    @Override
    public void checkGoogleAccount(GoogleSignInAccount lastSignedInAccount) {
        if (lastSignedInAccount != null) {
            // Verify that the account is a nearsoftian account
            String email = lastSignedInAccount.getEmail();
            if (email != null && email.matches(EMAIL_REGEX)) {
                signInSuccess();
            } else {
                signInError();
            }
        }
    }
}
