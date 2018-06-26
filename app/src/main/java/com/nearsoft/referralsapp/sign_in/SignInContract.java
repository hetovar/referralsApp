package com.nearsoft.referralsapp.sign_in;

public interface SignInContract {
    interface SingInView{
        void showJobListingActivity();
    }

    interface SignInPresenter{
        void handleSignInWithGoogleClick();
    }
}
