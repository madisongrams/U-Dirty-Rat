package edu.gatech.jjmae.u_dirty_rat.model;

/**
 * The abstract class that is the outline for user and admin
 * Created by Madison on 9/29/2017.
 */

public abstract class AbstractUser {

    private String username;
    private boolean isAdmin;
    private String email;

    /**
     * a constructor for AbstractUser
     *
     * @param username the username
     * @param isAdmin the administrator identifier
     *
     */
    public AbstractUser(String username, boolean isAdmin, String email) {
        this.username = username;
        this.isAdmin = isAdmin;
        this.email = email;
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

}
