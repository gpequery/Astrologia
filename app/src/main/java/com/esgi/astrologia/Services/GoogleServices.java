package com.esgi.astrologia.Services;

import android.app.Activity;

import com.esgi.astrologia.SigninActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * Created by Greg on 14/11/2017.
 */

public class GoogleServices {
    private static GoogleServices googleService;

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
                .build();
    }

    public GoogleSignInClient get_GoogleApiClient_builder(Activity activity) {
        GoogleSignInOptions gso = this.get_sign_in_options();

        return GoogleSignIn.getClient(activity, gso);
    }

    public GoogleSignInAccount get_last_connection(SigninActivity activity) {
        return GoogleSignIn.getLastSignedInAccount(activity);
    }
}