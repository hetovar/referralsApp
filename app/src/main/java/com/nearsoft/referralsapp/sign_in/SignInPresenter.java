package com.nearsoft.referralsapp.sign_in;

public class SignInPresenter implements SignInContract.SignInPresenter {
    private SignInContract.SingInView mView;

    public SignInPresenter(SignInContract.SingInView mView) {
        this.mView = mView;
    }

    @Override
    public void handleSignInWithGoogleClick() {
        mView.showJobListingActivity();
    }
}
