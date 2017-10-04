package edu.gatech.jjmae.u_dirty_rat;

/**
 * The abstract class that is the outline for user and admin
 * Created by Madison on 9/29/2017.
 */

public abstract class AbstractUser {

    private String username;

    /**
     * a constructor for AbstractUser
     *
     * @param username the username
     *
     */
    public AbstractUser(String username) {
        this.username = username;
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


}
