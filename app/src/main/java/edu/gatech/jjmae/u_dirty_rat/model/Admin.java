package edu.gatech.jjmae.u_dirty_rat.model;

/**
 * The administrator for the user
 * Created by Madison on 9/29/2017.
 */
public class Admin extends AbstractUser {

    /**
     * a two param constructor for Admin
     *
     * @param username the username
     * @param email the admin's email
     *
     */
    public Admin(String username, String email) {
        super(username, true, email);
    }

}
