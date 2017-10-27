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
    public User(String username, String password, boolean isBanned) {
        super(username, false, null, password, isBanned);
    }

    /**
     * a two parameter constructor for User
     *
     * @param username the username
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
