package com.nearsoft.referralsapp.sign_in;

import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class SignInActivityPresenter implements SignInActivityContract.SignInPresenter {
    private SignInActivityContract.SingInView mView;
    private static final String NEARSOFT_EMAIL_REGEX = "^[a-zA-Z]+@nearsoft.com$";

    SignInActivityPresenter(SignInActivityContract.SingInView mView) {
        this.mView = mView;
    }

    @Override
    public void handleSignInWithGoogleClick(View view) {
        if(view != null)
            mView.startAccountsActivity();
    }

    @Override
    public void signInSuccess() {
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
            String email = lastSignedInAccount.getEmail();
            if (email != null && email.matches(NEARSOFT_EMAIL_REGEX)) {
                signInSuccess();
            } else {
                signInError();
            }
        }
    }
}
