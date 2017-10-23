package edu.gatech.jjmae.u_dirty_rat.model;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

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
            Admin admin = new Admin(user, email, encryptedPassword);
            currentUser = admin;
            admins.put(user, admin);
        } else {
            User newUser = new User(user, email, encryptedPassword);
            currentUser = newUser;
            users.put(user, newUser);
        }

        return null;
    }

    /**
     * load the model from a custom text file
     *
     * @param reader  the file to read from
     */
    public static void loadFromText(BufferedReader reader) {
        System.out.println("Loading Text File");
        usernamesPasswords.clear();
        users.clear();
        admins.clear();
        try {
            String countStr = reader.readLine();
            Log.d("UserData", "loadFromText: " + countStr);
            assert countStr != null;
            int count = Integer.parseInt(countStr);
            // get secret key
            String data = reader.readLine();
            Log.d("UserData", "loadFromText: " + data);
            byte[] encoded = Base64.decode(data, Base64.URL_SAFE|Base64.NO_WRAP);
            key = new SecretKeySpec(encoded, "AES");
            //then read in each user to model
            for (int i = 0; i < count; i++) {
                String line = reader.readLine();
                Log.d("User Data", "loadFromText: " + line);
                AbstractUser u = AbstractUser.parseEntry(line);
                if (u.getIsAdmin()) {
                    admins.put(u.getUsername(), (Admin) u);
                } else {
                    users.put(u.getUsername(), (User) u);
                }

                usernamesPasswords.put(u.getUsername(), u.getPassword());
            }
            //be sure and close the file
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done loading text file with " + usernamesPasswords.size() + " users");

    }

    public static boolean saveText(File file) {
        System.out.println("Saving as a text file");
        try {
            PrintWriter pw = new PrintWriter(file);
            saveAsText(pw);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("UserData", "Error opening the text file for save!");
            return false;
        }

        return true;
    }

    static void saveAsText(PrintWriter writer) {
        System.out.println("UserData saving: " + usernamesPasswords.size() + " users" );
        writer.println(usernamesPasswords.size());
        // also saving key
        byte[] encoded = key.getEncoded();
        writer.println(Base64.encodeToString(encoded, Base64.URL_SAFE|Base64.NO_WRAP));
        for(String u : users.keySet()) {
            users.get(u).saveAsText(writer);
        }
        for (String a : admins.keySet()) {
            admins.get(a).saveAsText(writer);
        }
    }

    /**
     * sets up private key and cipher first time around
     * @return success or failure of key and cipher creation
     */
    private static boolean setUpKey() {
        if (key != null) {
            return true;
        }

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            key = keyGen.generateKey();
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
            String encryptedPass = Base64.encodeToString(encryptedBytes, Base64.URL_SAFE|Base64.NO_WRAP);
            Log.d("UserData", encryptedPass);
            return encryptedPass;
        } catch (Exception e) {
            Log.e(e.getMessage(), "Encryption failed");
            return null;
        }

    }

}