package com.esgi.astrologia.Utils;

import java.util.Date;

public class User {
    private int userId;
    private Date birthdate;

    public User(int userId, Date birthdate) {
        this.userId = userId;
        this.birthdate = birthdate;
    }

    public User(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getBirthdate() {
        return birthdate;
    }

}
