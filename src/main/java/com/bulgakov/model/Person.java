package com.bulgakov.model;

/**
 * @author Bulgakov
 * @since 17.02.2017
 */
public abstract class Person {
    public Integer personId;
    public String email;
    public String password;
    public String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getPersonId() {

        return personId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
