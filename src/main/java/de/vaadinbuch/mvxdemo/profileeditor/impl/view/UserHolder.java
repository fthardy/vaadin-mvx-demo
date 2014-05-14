package de.vaadinbuch.mvxdemo.profileeditor.impl.view;

import java.util.Date;

import de.vaadinbuch.mvxdemo.domain.User;

/**
 * Ein Adapter, der den zu bearbeitenden Benutzer hält.
 * 
 * @author Frank Hardy
 */
public class UserHolder {

    private User user;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return this.user != null ? this.user.getFirstName() : "";
    }

    public void setFirstName(String firstName) {
        if (this.user != null) {
            this.user.setFirstName(firstName);
        }
    }

    public String getLastName() {
        return this.user != null ? this.user.getLastName() : "";
    }

    public void setLastName(String lastName) {
        if (this.user != null) {
            this.user.setLastName(lastName);
        }
    }

    public String getEmailAddress() {
        return this.user != null ? this.user.getEmailAddress() : "";
    }

    public void setEmailAddress(String emailAddress) {
        if (this.user != null) {
            this.user.setEmailAddress(emailAddress);
        }
    }

    public Date getDateOfBirth() {
        return this.user != null ? this.user.getDateOfBirth() : null;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        if (this.user != null) {
            this.user.setDateOfBirth(dateOfBirth);
        }
    }
}
