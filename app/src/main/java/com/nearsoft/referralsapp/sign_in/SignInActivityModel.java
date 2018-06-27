package com.nearsoft.referralsapp.sign_in;

import android.content.Intent;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.nearsoft.referralsapp.sign_in.SignInActivityContract.SignInModel;

public class SignInActivityModel implements SignInModel {
    private static final String EMAIL_REGEX = "^[a-zA-Z]+@nearsoft.com$";
    private SignInActivityPresenter mPresenter;

    public SignInActivityModel(SignInActivityPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void handleSignInWithGoogle(GoogleSignInAccount account) {
        // If account is different than null than user has already sign in.
        if (account != null) {
            // Verify that the account is a nearsoftian account
            String email = account.getEmail();
            if (email != null && email.matches(EMAIL_REGEX)) {
                mPresenter.signInSuccess();
            } else {
                mPresenter.signInError();
            }
        }
    }

}
