package edu.gatech.jjmae.u_dirty_rat.model;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * The User data class for the user
 * Created by Madison on 9/29/2017.
 */


public class UserData {

    private static final HashMap<String, String> usernamesPasswords = new HashMap<String, String>();
    private static final Map<String, User> users = new HashMap<String, User>();
    private static final Map<String, Admin> admins = new HashMap<String, Admin>();

    private static SecretKey key;
    private static Cipher cipher;

    private static AbstractUser currentUser;
    private static int tries = 0;
    private static boolean lockout = false;


    /**
     * map of usernames to user objects
     * @return map
     */
    public static Map<String, User> getUsers() {
        return users;
    }
    /**
     * map of usernames to admin objects
     * @return map
     */
    public static Map<String, Admin> getAdmins() {
        return admins;
    }

    /**
     * getter for current user
     * @return the current user using the app
     */
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
     * @param userParam the username entered
     * @param password the password entered
     * @return error message when there's an error, and null on success
     */

    public static String login(String user, String password) {
        if (tries >= 3) {
            lockout = true;
        }
        if (lockout) {
            return "You have had too many login attempts and have been locked out.";
        }

        user = user.toLowerCase();
        String encryptedPassword = encryptPassword(password);
        if (encryptedPassword == null) {
            return "There was an issue on our end! Please try logging in again.";
        }

        if (!(password == null) && usernamesPasswords.containsKey(user) &&
                usernamesPasswords.get(user).equals(encryptedPassword)) {
            Admin isAdmin = admins.get(user);
            User isUser = users.get(user);

            if (isAdmin != null) {
                currentUser = isAdmin;
            } else {
                if (isUser.getIsBanned()) {
                    return "You have been banned. Please contact an admin for more details.";
                }
                currentUser = isUser;
            }
            return null;
        } else {
            tries += 1;
            return "Username and Password combination are incorrect.";
        }
    }

    /**
     * backend for user registration
     * @param userParam username of new user
     * @param password user's password
     * @param isAdmin whether or not a user is an admin
     * @param emailParam the user's email address
     * @return error message or null if successful registration
     */
    public static String register(String user, String password, boolean isAdmin, String email) {
        user = user.toLowerCase();
        email = email.toLowerCase();
        if (lockout) {
            return "You have had too many login attempts and have been locked out.";
        }

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
            User newUser = new User(user, email, encryptedPassword, false);
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
        Log.d("UserData:","Loading Text File");
        usernamesPasswords.clear();
        users.clear();
        admins.clear();
        try {
            String countStr = reader.readLine();
            Log.d("UserData", "loadFromText: " + countStr);
            assert countStr != null;
            int count = Integer.parseInt(countStr);
            //get lockout
            //String boolString = reader.readLine();
            //lockout = Boolean.getBoolean(boolString);
            // get secret key
            String data = reader.readLine();
            Log.d("UserData", "loadFromText: " + data);
            byte[] encoded = Base64.decode(data, Base64.URL_SAFE|Base64.NO_WRAP);
            key = new SecretKeySpec(encoded, "AES");
            //then read in each user to model
            for (int i = 0; i < count; i++) {
                String line = reader.readLine();
                //Log.d("User Data", "loadFromText: " + line);
                AbstractUser u = AbstractUser.parseEntry(line);
                if (u.getIsAdmin()) {
                    admins.put(u.getUsername(), (Admin) u);
                } else {
                    users.put(u.getUsername(), (User) u);
                }

                usernamesPasswords.put(u.getUsername(), u.getPassword());
            }
            //be sure and close the file
            //reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        Log.d("UserData:", "Done loading text file with " + usernamesPasswords.size() + " users");

    }

    /**
     * static method used to save data as text file
     * @param file file we are writing to
     */
    public static void saveText(File file) {
        Log.d("UserData:", "Saving as a text file");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
            saveAsText(pw);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("UserData", "Error opening the text file for save!");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }

    }

    /**
     * used to save data as a text file given a print writer
     * saves the number of users, the secret key and every user's data
     * @param writer writer we are writing to
     */
    private static void saveAsText(PrintWriter writer) {
        Log.d("UserData:", "UserData saving: " + usernamesPasswords.size() + " users" );
        writer.println(usernamesPasswords.size());
        //writer.println(Boolean.toString(lockout));
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
     * method to get user object from username
     * @param username name of user looking for
     * @return user that was found
     */
    public static User getUser(String username) {
        return users.get(username);
    }

    /**
     * method to get admin object from username
     * @param username name of admin looking for
     * @return admin that was found
     */
    public static Admin getAdmin(String username) {
        return admins.get(username);
    }

    /**
     * sets up private key first time around
     * new key should only be created if nothing has yet been written into memory
     * (key encoding is saved to a text file for later use)
     * @return success or failure of key creation
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
            //Log.e(e.getMessage(), "setUpKey: exception initializing key");
            return false;
        }
    }

    /**
     * sets up cipher first time around using the private key
     * @return whether or not cipher was successful
     */
    private static boolean setUpCipher() {
        if (cipher != null) {
            return true;
        }
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (Exception e) {
            //Log.e(e.getMessage(), "setUpCipher: exception setting up cipher");
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
            return Base64.encodeToString(encryptedBytes, Base64.URL_SAFE|Base64.NO_WRAP);
            //Log.d("UserData", encryptedPass);
            //return encryptedPass;
        } catch (Exception e) {
            //Log.e(e.getMessage(), "Encryption failed");
            return null;
        }

    }

}