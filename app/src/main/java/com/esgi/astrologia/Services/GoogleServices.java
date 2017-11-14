package com.esgi.astrologia.Services;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Greg on 14/11/2017.
 */

public class GoogleServices {
    private static GoogleServices googleService;

    private GoogleServices() {

    }

    public static GoogleServices getInstance() {
        if(googleService == null) {
            googleService = new GoogleServices();
        }

        return googleService;
    }

    public GoogleSignInOptions get_sign_in_options() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    public GoogleApiClient get_GoogleApiClient_builder(Activity activity) {
        GoogleSignInOptions options = this.get_sign_in_options();

        return new GoogleApiClient.Builder(activity)
                .enableAutoManage((FragmentActivity) activity, null )
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();
    }
}
