package com.nearsoft.referralsapp.sign_in;

import android.view.View;

public class SignInActivityPresenter implements SignInActivityContract.SignInPresenter {
    private SignInActivityContract.SingInView mView;

    public SignInActivityPresenter(SignInActivityContract.SingInView mView) {
        this.mView = mView;
    }

    @Override
    public void handleSignInWithGoogleClick(View view) {
        mView.showJobListingActivity();
    }
}
