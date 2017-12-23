package com.esgi.astrologia;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.esgi.astrologia.Services.CalendarServices;
import com.esgi.astrologia.Services.GoogleServices;
import com.esgi.astrologia.Utils.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.model.people.Person;

import java.util.Calendar;

import static com.esgi.astrologia.Services.GoogleServices.REQUEST_CODE_SIGN_IN;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MON_TAG = "TAG_SA";
    private GoogleServices google_service = GoogleServices.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        GoogleApiClient googleClient = google_service.get_GoogleApiClient_builder(this);
        google_service.setGoogleClient(googleClient);

        SignInButton signInWithGoogle = findViewById(R.id.sign_in_with_google);
        signInWithGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_with_google:
                signInWithGoogle_firstStep();
                break;
            case R.id.sign_in_without_account:
                sign_in_without_account();
                break;
        }
    }

    private void sign_in_without_account() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
            Calendar bithdate = Calendar.getInstance();
            bithdate.set(Calendar.YEAR, selectedYear);
            bithdate.set(Calendar.MONTH, selectedMonth);
            bithdate.set(Calendar.DAY_OF_MONTH, selectedDay);

            User currentUser = new User(bithdate);
            finishConnection(currentUser);
        }, currentYear, currentMonth, currentDay);

        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void finishConnection(User currentUser) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void signInWithGoogle_firstStep() {
        GoogleSignInAccount account = GoogleServices.getInstance().get_last_connection(this);

        if (account == null) {
            Intent googleSignInIntent = google_service.getGoogleSignInIntent();
            startActivityForResult(googleSignInIntent, REQUEST_CODE_SIGN_IN);
        } else {
            signInWithGoogle_finalStep(account);
        }
    }
    
    private void signInWithGoogle_finalStep(GoogleSignInAccount account) {
        if (google_service.hasConnectedGooglePlus()) {
            Person person = google_service.getCurrentPerson();
            if (person != null && person.getBirthday() != null) {
                Calendar birthdate = CalendarServices.stringToCalendar_en(person.getBirthday());

                User currentUser = new User(account.getGivenName(), birthdate);
                finishConnection(currentUser);
            } else {
                google_service.disconnectAccount();

                String msg = getString(R.string.birthdate_not_found) + "\n" + getString(R.string.without_account_text);
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            }
        } else {
            google_service.disconnectAccount();

            String msg = getString(R.string.count_not_found) + "\n" + getString(R.string.without_account_text);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            GoogleSignInAccount account = google_service.getSignInAccountFromData(data);

            if(account != null) {
                signInWithGoogle_finalStep(account);
            }
        }
    }
}
