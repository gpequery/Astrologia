package com.esgi.astrologia.Services;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.esgi.astrologia.SigninActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;


public class GoogleServices {
    private static GoogleServices googleService;
    public static final int REQUEST_CODE_SIGN_IN = 0;

    private GoogleServices() {
    }

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
}