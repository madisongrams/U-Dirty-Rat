package edu.gatech.jjmae.u_dirty_rat.model;

import android.util.Log;

import java.io.PrintWriter;

/**
 * The abstract class that is the outline for user and admin
 * Created by Madison on 9/29/2017.
 */

public abstract class AbstractUser {

    private String username;
    private boolean isAdmin;
    private String email;
    private final String password;
    private boolean isBanned;

    /**
     * a constructor for AbstractUser
     *
     * @param username the username
     * @param isAdmin the administrator identifier
     * @param email user's email
     * @param password user's password
     * @param isBanned whether or not user is banned
     */
    AbstractUser(String username, boolean isAdmin, String email, String password,
                 boolean isBanned) {
        this.username = username;
        this.isAdmin = isAdmin;
        this.email = email;
        this.password = password;
        this.isBanned = isBanned;
    }
    /**
     * This is a static factory method that constructs a user given a text line in the correct
     * format.
     * It assumes that a user is in a single string with each attribute separated by a tab character
     * The order of the data is assumed to be:
     *
     * 0 - username
     * 1 - password
     * 2 - isAdmin
     * 3 - email
     * 4 - isBanned
     *
     * @param line  the text line containing the data
     * @return the user object
     */
    public static AbstractUser parseEntry(String line) {
        assert line != null;
        Log.d("Abstract User", line);
        String[] tokens = line.split("\t");
        //assert tokens.length == 5;

        if ("true".equals(tokens[2])) {
            return new Admin(tokens[0], tokens[3], tokens[1]);
        }
        return new User(tokens[0], tokens[3], tokens[1], Boolean.valueOf(tokens[4]));
    }

    /**
     * Method that is used to write user data to a file with a given order of the instance variables
     * @param writer print writer used to write data to
     */
    public void saveAsText(PrintWriter writer) {
        //Log.d("AbstractUser", "saving userdata: " + username + "\t" +
        // password + "\t" + String.valueOf(isAdmin) + "\t" + email);
        writer.println(username + "\t" + password + "\t" + String.valueOf(isAdmin) + "\t" + email
        + "\t" + String.valueOf(isBanned));
    }
    /**
     * a setter for username parameter
     *
     * @param username the new username
     *
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * a getter for username parameter
     *
     * @return username
     *
     */
    public String getUsername() {
        return username;
    }

    /**
     * a setter for isAdmin parameter
     *
     * @param isAdmin the new admin data
     *
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * a getter for isAdmin parameter
     *
     * @return isAdmin
     *
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * a setter for the email parameter
     *
     * @param email the new email
     *
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * a getter for the email parameter
     *
     * @return email
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     * getter for password
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * its a getter
     * @return whether or not user is banned
     */
    public boolean getIsBanned() {
        return isBanned;
    }

    void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }
}
