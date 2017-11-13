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
     *
     */
    public Admin(String username, String email, String password) {
        super(username, true, email, password, false);
    }

    public void banUser(User user) {
        user.ban();
    }

    public void unBanUser(User user) {
        user.unBan();
    }

}
