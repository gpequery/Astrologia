package com.esgi.astrologia;

import android.accounts.Account;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.esgi.astrologia.Services.GoogleServices;
import com.esgi.astrologia.Utils.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.util.Calendar;
import java.util.Date;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private GoogleApiClient mGoogleApiClient;
    private SignInButton sign_in_with_google;
    private int mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        GoogleServices googleServices = GoogleServices.getInstance();
        mGoogleApiClient =  googleServices.get_GoogleApiClient_builder(this);

        sign_in_with_google = (SignInButton) findViewById(R.id.sign_in_with_google);
        sign_in_with_google.setSize(SignInButton.SIZE_WIDE);

        sign_in_with_google.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_with_google:
                Log.i("MON_TAG", "Sign in with google");
                break;
            case R.id.sign_in_without_account:
                Log.i("MON_TAG", "Sign in without account");
                sign_in_without_account();
                break;
        }
    }

    private void sign_in_without_account() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, SigninActivity.this, currentYear, currentMonth, currentDay);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
        Log.i("MON_TAG", "onDateSet");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, selectedYear);
        calendar.set(Calendar.MONTH, selectedMonth);
        calendar.set(Calendar.DAY_OF_MONTH, selectedDay);

        Date birthdate = calendar.getTime();
        User currentUser = new User(birthdate);

        goHome(currentUser);

        Log.i("MON_TAG", "Date : " + calendar.get(Calendar.YEAR) + " / " + calendar.get(Calendar.MONTH) + " / " + calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void goHome(User currentUser) {
        Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
        startActivity(intent);
    }


    private void signIn(View view) {
        Log.i("MON_TAG", "vie");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("MON_TAG", "onActivityResult");
    }


}
