package com.esgi.astrologia;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.esgi.astrologia.Constants.Preferences;
import com.esgi.astrologia.Services.CalendarServices;
import com.esgi.astrologia.Utils.User;
import com.google.gson.Gson;
public class HomeActivity extends AppCompatActivity {
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Gson gson = new Gson();
        String json = preferences.getString(Preferences.USER, null);
        currentUser = gson.fromJson(json, User.class);

        Log.i("TAG_HA", CalendarServices.calendarToString(currentUser.getBirthdate()));

        setContentView(R.layout.activity_home);
    }
}
