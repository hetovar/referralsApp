package com.nearsoft.referralsapp.sign_in;

import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class SignInActivityPresenter implements SignInActivityContract.SignInPresenter {
    private SignInActivityContract.SingInView mView;
    private SignInActivityContract.SignInModel mModel;

    public SignInActivityPresenter(SignInActivityContract.SingInView mView, GoogleSignInClient mGoogleSignInClient) {
        this.mView = mView;
        this.mModel = new SignInActivityModel(this);
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
        mModel.handleSignInWithGoogle(lastSignedInAccount);
    }
}
