package edu.gatech.jjmae.u_dirty_rat;

import org.junit.Test;

import java.util.HashMap;

import edu.gatech.jjmae.u_dirty_rat.model.Admin;
import edu.gatech.jjmae.u_dirty_rat.model.User;
import edu.gatech.jjmae.u_dirty_rat.model.UserData;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Madison on 11/13/2017.
 * contains tests for userdata
 */

public class UserDataTest {
    // tests login functionality
    @Test
    public void testLogin() {
        HashMap<String, User> users = UserData.getUsers();
        HashMap<String, Admin> admins = UserData.getAdmins();
        assertFalse(users.containsKey("abcd"));
        assertFalse(admins.containsKey("asdf"));

        String error = UserData.register("abcd", "password", false, "@.");
        assertNull(error);
        users = UserData.getUsers();
        admins = UserData.getAdmins();
        assertTrue(users.containsKey("abcd"));
        assertFalse(admins.containsKey("abcd"));
        assertEquals(UserData.getCurrentUser().getUsername(), "abcd");

        error = UserData.register("asdf", "password", true, "@.");
        assertNull(error);
        users = UserData.getUsers();
        admins = UserData.getAdmins();
        assertTrue(admins.containsKey("asdf"));
        assertFalse(users.containsKey("asdf"));
        assertEquals(UserData.getCurrentUser().getUsername(), "asdf");

        error = UserData.register("abcd", "password", false, "@.");
        assertEquals(error, "That username is taken.");

    }
}
