package com.nearsoft.referralsapp.sign_in;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Local unit test for the sign in presenter class.
 */
public class SignInPresenterTest {
    @Mock
    private SignInContract.SingInView mView;

    private SignInPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new SignInPresenter(mView);
    }

    @Test
    public void handleSignInWithGoogleClick() {
        mPresenter.handleSignInWithGoogleClick();
        verify(mView).showJobListingActivity();
    }
}