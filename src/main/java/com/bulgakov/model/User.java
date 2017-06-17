package com.bulgakov.model;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public class User extends Person{
    private String surname;
    private String secondName;
    private Double identityCode;
    private String qualification;
    private String role;
    private User boss;

    public User(Integer personId, String email, String password, String name, String surname, String secondName, Double identityCode, String qualification, String role, User boss) {
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.identityCode = identityCode;
        this.qualification = qualification;
        this.role = role;
        this.boss = boss;
        super.personId = personId;
        super.email = email;
        super.password = password;
    }

    public User(Integer personId,String email,String password, String name, String surname, String secondName, Double identityCode, String qualification, String role) {
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.identityCode = identityCode;
        this.qualification = qualification;
        this.role = role;
        super.personId = personId;
        super.email = email;
        super.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Double getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(Double identityCode) {
        this.identityCode = identityCode;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getBoss() {
        return boss;
    }

    public void setBoss(User boss) {
        this.boss = boss;
    }

    public User(User user) {
        super.email = user.getEmail();
        super.password = user.getPassword();
    }

    public User() {}
}
