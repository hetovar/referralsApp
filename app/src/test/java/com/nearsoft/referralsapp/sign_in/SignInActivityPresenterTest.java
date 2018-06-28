package com.nearsoft.referralsapp.sign_in;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Local unit test for the sign in presenter class.
 */
public class SignInActivityPresenterTest {
    @Mock
    private SignInActivityContract.SingInView mView;
    @Mock
    private View view;

    private SignInActivityPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new SignInActivityPresenter(mView);
    }

    @Test
    public void shouldHandleSignInWithGoogle(){
        mPresenter.handleSignInWithGoogleClick(view);

        verify(mView).startAccountsActivity();
    }

    @Test
    public void shouldNotHandleSignInWithGoogle(){
        mPresenter.handleSignInWithGoogleClick(null);

        verify(mView, never()).startAccountsActivity();
    }

}