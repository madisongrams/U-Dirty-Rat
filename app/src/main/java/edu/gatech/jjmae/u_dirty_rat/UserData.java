package edu.gatech.jjmae.u_dirty_rat;

import android.util.Base64;
import android.util.Log;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Created by Madison on 9/17/2017.
 */

//TODO: return specific reasons for login and registration failure
public class UserData {
    private static HashMap<String, String> usernamesPasswords = new HashMap<String, String>();
    private static HashMap<String, User> users = new HashMap<String, User>();
    private static HashMap<String, Admin> admins = new HashMap<String, Admin>();

    private static KeyGenerator keyGen;
    private static SecretKey key;
    private static Cipher cipher;

    public HashMap<String, User> getUsers() {
        return users;
    }

    public HashMap<String, Admin> getAdmins() {
        return admins;
    }

    /**
     * A method that allows for login functionality
     * @param user the username entered
     * @param password the password entered
     * @return whether or not the username and password are correct
     */
    public static boolean login(String user, String password) {
        // here is the default login info, commented out to test registration
        //TODO: just remove this entirely once registration is functional
//        String defaultPass = encryptPassword("pass");
//        if (defaultPass == null) {
//            return false;
//        }
//        usernamesPasswords.put("user", defaultPass);

        String encryptedPassword = encryptPassword(password);
        if (encryptedPassword == null) {
            return false;
        }
        return !(user == null) && !(password == null) && usernamesPasswords.containsKey(user)
                && usernamesPasswords.get(user).equals(encryptedPassword);
    }

    /**
     * backend for user registration
     * @param user username of new user
     * @param password user's password
     * @param isAdmin whether or not a user is an admin
     * @return whether or not registration was a success
     */
    public static boolean register(String user, String password, boolean isAdmin) {
        if (usernamesPasswords.containsKey(user)) {
            return false;
        }
        String encryptedPassword = encryptPassword(password);
        if (encryptedPassword == null) {
            return false;
        }
        usernamesPasswords.put(user, encryptedPassword);
        if (isAdmin) {
            admins.put(user, new Admin(user));
        } else {
            users.put(user, new User(user));
        }
        return true;
    }

    /**
     * sets up private key and cipher first time around
     * @return success or failure of key and cipher creation
     */
    private static boolean setUpKey() {
        if (keyGen != null) {
            return true;
        }
        try {
            keyGen = KeyGenerator.getInstance("AES");
            key = keyGen.generateKey();
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;

        } catch (Exception e) {
            Log.e(e.getMessage(), "setUpKey: exception initializing key");
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