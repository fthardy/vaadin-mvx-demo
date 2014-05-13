package de.vaadinbuch.mvxdemo.domain;

import java.util.Date;

/**
 * Repräsentiert einen Benutzer.
 * 
 * @author Frank Hardy
 */
public class User {

    private String userId;
    private String passwordHash;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private Date dateOfBirth;

    public User() {
        // leer
    }

    public User(User user) {
        this.userId = user.userId;
        this.passwordHash = user.passwordHash;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.emailAddress = user.emailAddress;
        this.dateOfBirth = user.dateOfBirth;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
