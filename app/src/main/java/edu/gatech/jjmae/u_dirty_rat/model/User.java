package edu.gatech.jjmae.u_dirty_rat.model;

/**
 * The User class for the user
 * Created by Madison on 9/29/2017.
 */

public class User extends AbstractUser {


    /**
     * a constructor for User
     *
     * @param username the username
     */
    public User(String username, String password) {
        super(username, false, null, password);
    }

    /**
     * a two parameter constructor for User
     *
     * @param username the username
     */
    public User(String username, String email, String password) {
        super(username, false, email, password);
    }
}
