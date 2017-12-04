package com.esgi.astrologia;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.esgi.astrologia.Services.GoogleServices;
import com.esgi.astrologia.Utils.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import java.util.Calendar;
import java.util.Date;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    private GoogleSignInClient mGoogleApiClient;
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        GoogleServices googleServices = GoogleServices.getInstance();
        mGoogleApiClient =  googleServices.get_GoogleApiClient_builder(this);

        SignInButton sign_in_with_google = findViewById(R.id.sign_in_with_google);

        sign_in_with_google.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_with_google:
                sign_in_with_google();
                break;
            case R.id.sign_in_without_account:
                sign_in_without_account();
                break;
        }
    }

    private void sign_in_with_google() {
        GoogleSignInAccount account = GoogleServices.getInstance().get_last_connection(this);

        if (account == null) {
            Log.i("MON_TAG", "account");
            Intent signInIntent = mGoogleApiClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();

            Log.i("MON_TAG", "personName : " + personName);
            Log.i("MON_TAG", "personGivenName : " + personGivenName);
            Log.i("MON_TAG", "personFamilyName : " + personFamilyName);
            Log.i("MON_TAG", "personEmail : " + personEmail);
            Log.i("MON_TAG", "personId : " + personId);
        }
        //TO DO else goHome with current user
    }

    private void sign_in_without_account() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.YEAR, selectedYear);
            calendar1.set(Calendar.MONTH, selectedMonth);
            calendar1.set(Calendar.DAY_OF_MONTH, selectedDay);

            Date birthdate = calendar1.getTime();
            User currentUser = new User(birthdate);

            goHome(currentUser);
        }, currentYear, currentMonth, currentDay);

        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void goHome(User currentUser) {
        Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("MON_TAG", "onActivityResult ");

        if(resultCode == RESULT_OK) {
            GoogleSignInAccount account = GoogleServices.getInstance().get_last_connection(this);

            if(account != null) {
                String personName = account.getDisplayName();
                String personGivenName = account.getGivenName();
                String personFamilyName = account.getFamilyName();
                String personEmail = account.getEmail();
                String personId = account.getId();

                Log.i("MON_TAG", "personName : " + personName);
                Log.i("MON_TAG", "personGivenName : " + personGivenName);
                Log.i("MON_TAG", "personFamilyName : " + personFamilyName);
                Log.i("MON_TAG", "personEmail : " + personEmail);
                Log.i("MON_TAG", "personId : " + personId);
            }
        }
    }
}
