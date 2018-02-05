package com.esgi.astrologia.Utils;

import android.util.Log;

import java.util.Calendar;

public class User {
    private String pseudo;

    private Calendar birthdate;

    public User(String pseudo, Calendar birthdate) {
        this.pseudo = pseudo;
        this.birthdate = birthdate;
    }

    public User(Calendar birthdate) {

        this.birthdate = birthdate;
    }

    public Calendar getBirthdate() {
        return birthdate;
    }

    public String getBirthdateString() {
        return formatDateNumber(Calendar.DAY_OF_MONTH) + "/" + formatDateNumber(Calendar.MONTH) + "/" +formatDateNumber(Calendar.YEAR);
    }

    private String formatDateNumber(int index) {
        int month = 0;
        if(index == Calendar.MONTH) {
            month = 1;
        }

        String number = String.valueOf(birthdate.get(index) + month);

        if (number.length() == 1) {
            number = "0" + number;
        }

        return number;
    }
}
