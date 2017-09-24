package edu.gatech.jjmae.u_dirty_rat;

import java.util.HashMap;

/**
 * Created by Madison on 9/17/2017.
 */

//TODO: probably make this class into a class for both registration & login
public class Login {
    private static HashMap<String, String> usernamesPasswords = new HashMap<String, String>();
    //TODO: move this hashmap into a more generic file for access by both login & registration

    /**
     * A method that allows for login functionality
     * @param user the username entered
     * @param password the password entered
     * @return whether or not the username and password are correct
     */
    public static boolean login(String user, String password) {
        usernamesPasswords.put("jjmae", "password");
        return !(user == null) && !(password == null) && usernamesPasswords.containsKey(user)
                && usernamesPasswords.get(user).equals(password);
    }

    //TODO: maybe add some basic encryption to the passwords?
}