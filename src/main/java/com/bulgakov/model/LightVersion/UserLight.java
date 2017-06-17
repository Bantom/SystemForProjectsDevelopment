package com.bulgakov.model.LightVersion;

import com.bulgakov.model.User;

import java.io.Serializable;

/**
 * @author Bulgakov
 * @since 21.02.2017
 */
public class UserLight implements Serializable{
    public Integer personId;
    public String email;
    public String password;
    public String name;
    public String surname;
    public String secondName;
    public Double identityCode;
    public String qualification;
    public String role;
    public Integer bossId;

    public UserLight(User user) {
        this.personId = user.getPersonId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.secondName = user.getSecondName();
        this.identityCode = user.getIdentityCode();
        this.qualification = user.getQualification();
        this.role = user.getRole();
        this.bossId = user.getBoss().getPersonId();
    }

    public UserLight() {
    }
}
