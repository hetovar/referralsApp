package com.nearsoft.referralsapp.sign_in;

import android.view.View;

public interface SignInActivityContract {
    interface SingInView{
        void showJobListingActivity();
    }

    interface SignInPresenter{
        void handleSignInWithGoogleClick(View view);
    }
}
