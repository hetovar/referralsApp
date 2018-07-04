package com.nearsoft.referralsapp.sign_in;

import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface SignInActivityContract {
    interface SingInView{
        void startHomeActivity();
        void startAccountsActivity();
        void displaySignInErrorMessage();
        void checkPreviousSignIn();
        void signOut();
    }

    interface SignInPresenter{
        void handleSignInWithGoogleClick(View view);
        void signInSuccess();
        void signInError();
        void checkGoogleAccount(GoogleSignInAccount lastSignedInAccount);
    }
}
