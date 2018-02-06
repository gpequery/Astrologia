package com.esgi.astrologia.Services;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.esgi.astrologia.SigninActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class GoogleServices {
    private static GoogleServices googleService;
    private GoogleApiClient mGoogleApiClient;
    public static final int REQUEST_CODE_SIGN_IN = 0;

    public static GoogleServices getInstance() {
        if (googleService == null) {
            googleService = new GoogleServices();
        }

        return googleService;
    }

    private GoogleSignInOptions get_sign_in_options() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .build();
    }

    public GoogleApiClient get_GoogleApiClient_builder(Activity activity) {
        GoogleSignInOptions gso = this.get_sign_in_options();

        return new GoogleApiClient.Builder(activity)
                .enableAutoManage((FragmentActivity) activity, null )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();
    }

    public GoogleSignInAccount get_last_connection(SigninActivity activity) {
        return GoogleSignIn.getLastSignedInAccount(activity);
    }

    public void setGoogleClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }

    public Intent getGoogleSignInIntent() {
        return Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    }

    public boolean hasConnectedGooglePlus() {
        return mGoogleApiClient.hasConnectedApi(Plus.API);
    }

    public Person getCurrentPerson() {
        return Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
    }

    public void disconnectAccount() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        mGoogleApiClient.reconnect();
    }

    public GoogleSignInAccount getSignInAccountFromData(Intent data) {
        return Auth.GoogleSignInApi.getSignInResultFromIntent(data).getSignInAccount();
    }
}