package edu.gatech.jjmae.u_dirty_rat.model;

/**
 * The User class for the user
 * Created by Madison on 9/29/2017.
 */

public class User extends AbstractUser {

    /**
     * constructor for user
     * @param username user's name
     * @param password user's password
     * @param isBanned whether or not user is banned
     */
    public User(String username, String password, boolean isBanned) {
        super(username, false, null, password, isBanned);
    }

    /**
     * constructor for user
     * @param username user's name
     * @param email user's email
     * @param password user's password
     * @param isBanned whether or not user is banned
     */
    public User(String username, String email, String password, boolean isBanned) {
        super(username, false, email, password, isBanned);
    }

    protected void ban() {
        setIsBanned(true);
    }

    protected void unBan() {
        setIsBanned(false);
    }
}
