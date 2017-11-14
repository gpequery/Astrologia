package com.esgi.astrologia;

import android.accounts.Account;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.esgi.astrologia.Services.GoogleServices;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class SigninActivity extends AppCompatActivity {
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        GoogleServices googleServices = GoogleServices.getInstance();
        mGoogleApiClient =  googleServices.get_GoogleApiClient_builder(this);

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);

        signInButton.setOnClickListener((view) -> { signIn(); } );
    }

    private void signIn() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account == null) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RESULT_OK);
        } else {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            Log.i("MON_TAG", "personName : " + personName);
            Log.i("MON_TAG", "personGivenName : " + personGivenName);
            Log.i("MON_TAG", "personFamilyName : " + personFamilyName);
            Log.i("MON_TAG", "personEmail : " + personEmail);
            Log.i("MON_TAG", "personId : " + personId);
            Log.i("MON_TAG", "personPhoto : " + personPhoto);
        }
    }
}
