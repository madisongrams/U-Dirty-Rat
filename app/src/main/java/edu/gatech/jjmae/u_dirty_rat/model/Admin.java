package edu.gatech.jjmae.u_dirty_rat.model;

/**
 * The administrator for the user
 * Created by Madison on 9/29/2017.
 */
public class Admin extends AbstractUser {


// --Commented out by Inspection START (11/9/17, 10:58 AM):
//    /**
//     * a constructor for Admin
//     *
//     * @param username the username
//     * @param password the user's password
//     *
//     */
//    public Admin(String username, String password) {
//        super(username, true, null, password, false);
//    }
// --Commented out by Inspection STOP (11/9/17, 10:58 AM)

    /**
     * a two param constructor for Admin
     *
     * @param username the username
     * @param email the admin's email
     * @param password admin's password
     */
    public Admin(String username, String email, String password) {
        super(username, true, email, password, false);
    }

    /**
     * bans a user
     * @param user user to be banned
     */
    public void banUser(User user) {
        user.ban();
    }

    /**
     * unbans a user
     * @param user to be unbanned
     */
    public void unBanUser(User user) {
        user.unBan();
    }

}
