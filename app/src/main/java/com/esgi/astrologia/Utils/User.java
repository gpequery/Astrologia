package com.esgi.astrologia.Utils;

import java.util.Date;

/**
 * Created by Greg on 04/12/2017.
 */

public class User {
    private Date birthdate;

    public User(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
