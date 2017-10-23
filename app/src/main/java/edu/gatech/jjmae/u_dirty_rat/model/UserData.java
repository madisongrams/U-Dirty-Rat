package edu.gatech.jjmae.u_dirty_rat.model;

import android.util.Base64;
import android.util.Log;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * The User data class for the user
 * Created by Madison on 9/29/2017.
 */

//TODO: return specific reasons for login and registration failure
public class UserData {
    private static HashMap<String, String> usernamesPasswords = new HashMap<String, String>();
    private static HashMap<String, User> users = new HashMap<String, User>();
    private static HashMap<String, Admin> admins = new HashMap<String, Admin>();

    private static SecretKey key;
    private static Cipher cipher;

    private static AbstractUser currentUser;



    /**
     *
     * all getters and setters for UserData
     *
     */
    public static HashMap<String, User> getUsers() {
        return users;
    }

    public static HashMap<String, Admin> getAdmins() {
        return admins;
    }

    public static AbstractUser getCurrentUser() {
        return currentUser;
    }



    /**
     * @param user the new current user
     */
    public static void setCurrentUser(AbstractUser user) {
        currentUser = user;
    }

    /**
     * A method that allows for login functionality
     * @param user the username entered
     * @param password the password entered
     * @return error message when there's an error, and null on success
     */
    public static String login(String user, String password) {
        user = user.toLowerCase();
        // here is the default login info, commented out to test registration
        //TODO: just remove this entirely once registration is functional
//        String defaultPass = encryptPassword("pass");
//        if (defaultPass == null) {
//            return false;
//        }
//        usernamesPasswords.put("user", defaultPass);

        String encryptedPassword = encryptPassword(password);
        if (encryptedPassword == null) {
            return "There was an issue on our end! Please try logging in again.";
        }
        if (!(user == null) && !(password == null) && usernamesPasswords.containsKey(user)
                && usernamesPasswords.get(user).equals(encryptedPassword)) {
            Admin isAdmin = admins.get(user);
            User isUser = users.get(user);
            if (isAdmin != null) {
                currentUser = isAdmin;
            } else {
                currentUser = isUser;
            }
            return null;
        } else {
            return "Username and Password combination are incorrect.";
        }
    }

    /**
     * backend for user registration
     * @param user username of new user
     * @param password user's password
     * @param isAdmin whether or not a user is an admin
     * @param email the user's email address
     * @return error message or null if successful registration
     */
    public static String register(String user, String password, boolean isAdmin, String email) {
        user = user.toLowerCase();
        email = email.toLowerCase();
        if (usernamesPasswords.containsKey(user)) {
            return "That username is taken.";
        }
        String encryptedPassword = encryptPassword(password);
        if (encryptedPassword == null) {
            return "There was an issue on our end! Please try logging in again.";
        }
        usernamesPasswords.put(user, encryptedPassword);
        if (isAdmin) {
            Admin admin = new Admin(user, email);
            currentUser = admin;
            admins.put(user, admin);
        } else {
            User newUser = new User(user, email);
            currentUser = newUser;
            users.put(user, newUser);
        }
        return null;
    }

    /**
     * sets up private key and cipher first time around
     * @return success or failure of key and cipher creation
     */
    private static boolean setUpKey() {
        if (key != null) {
            return true;
        }
        //TODO first try and get key from database, else create the new key
        // so something like...
        // if (key in database) {
        //      key = value from database;
        //      return true;
        // }
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            key = keyGen.generateKey();
            //TODO add to database
            return true;
        } catch (Exception e) {
            Log.e(e.getMessage(), "setUpKey: exception initializing key");
            return false;
        }
    }

    private static boolean setUpCipher() {
        if (cipher != null) {
            return true;
        }
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (Exception e) {
            Log.e(e.getMessage(), "setUpCipher: exception setting up cipher");
            return false;
        }
    }
    /**
     * method to encrypt a password with set cipher and key
     * @param password password to be encrypted
     * @return encrypted password if successful, else returns null
     */
    private static String encryptPassword(String password) {
        try {
            if (!setUpKey()) {
                return null;
            } else if (!setUpCipher()) {
                return null;
            }
            byte[] dataBytes = password.getBytes();
            byte[] encryptedBytes = cipher.doFinal(dataBytes);
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
        } catch (Exception e) {
            Log.e(e.getMessage(), "Encryption failed");
            return null;
        }

    }

}