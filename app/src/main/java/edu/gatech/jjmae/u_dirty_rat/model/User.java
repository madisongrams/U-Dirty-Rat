package edu.gatech.jjmae.u_dirty_rat.model;

/**
 * The User class for the user
 * Created by Madison on 9/29/2017.
 */

public class User extends AbstractUser {

// --Commented out by Inspection START (11/9/17, 11:02 AM):
//    /**
//     * constructor for user
//     * @param username user's name
//     * @param password user's password
//     * @param isBanned whether or not user is banned
//     */
//    public User(String username, String password, boolean isBanned) {
//        super(username, false, null, password, isBanned);
//    }
// --Commented out by Inspection STOP (11/9/17, 11:02 AM)

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

    void ban() {
        setIsBanned(true);
    }

    void unBan() {
        setIsBanned(false);
    }
}
