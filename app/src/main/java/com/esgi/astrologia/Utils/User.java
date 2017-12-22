package com.esgi.astrologia.Utils;

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
}
