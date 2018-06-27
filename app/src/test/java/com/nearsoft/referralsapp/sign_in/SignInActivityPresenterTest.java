package com.nearsoft.referralsapp.sign_in;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Local unit test for the sign in presenter class.
 */
public class SignInActivityPresenterTest {
    @Mock
    private SignInActivityContract.SingInView mView;

    private SignInActivityPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new SignInActivityPresenter(mView, mGoogleSignInClient);
    }

    @Test
    public void handleSignInWithGoogleClick() {
        mPresenter.handleSignInWithGoogleClick();
        verify(mView).displaySignInSuccessMessage();
    }
}