package com.esgi.astrologia;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.esgi.astrologia.Services.GoogleServices;
import com.esgi.astrologia.Utils.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static com.esgi.astrologia.Services.GoogleServices.REQUEST_CODE_SIGN_IN;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MON_TAG = "TAG_SA";
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        GoogleServices googleServices = GoogleServices.getInstance();
        mGoogleApiClient = googleServices.get_GoogleApiClient_builder(this);

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
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
        } else {
            if (mGoogleApiClient.hasConnectedApi(Plus.API)) {
                Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                if (person != null) {
                    try {
                        Date birthdate = SimpleDateFormat.getInstance().parse(person.getBirthday());

                        User currentUser = new User(Integer.parseInt(account.getId()), birthdate);
                        goHome(currentUser);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    mGoogleApiClient.disconnect();

                    String msg = getString(R.string.birthdate_not_found) + "\n" + getString(R.string.without_account_text);
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                }
            } else {
                mGoogleApiClient.disconnect();

                String msg = getString(R.string.count_not_found) + "\n" + getString(R.string.without_account_text);
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void sign_in_without_account() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
            calendar.set(Calendar.YEAR, selectedYear);
            calendar.set(Calendar.MONTH, selectedMonth);
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay);

            Date birthdate = calendar.getTime();
            User currentUser = new User(birthdate);

            goHome(currentUser);
        }, currentYear, currentMonth, currentDay);

        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }


    private void goHome(User currentUser) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount account = result.getSignInAccount();

            if(account != null) {
                if (mGoogleApiClient.hasConnectedApi(Plus.API)) {
                    Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                    if (person != null) {
                        try {
                            Date birthdate = SimpleDateFormat.getInstance().parse(person.getBirthday());

                            User currentUser = new User(Integer.parseInt(account.getId()), birthdate);
                            goHome(currentUser);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        mGoogleApiClient.disconnect();

                        String msg = getString(R.string.birthdate_not_found) + "\n" + getString(R.string.without_account_text);
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    }
                } else {
                    mGoogleApiClient.disconnect();

                    String msg = getString(R.string.count_not_found) + "\n" + getString(R.string.without_account_text);
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
