package edu.gatech.jjmae.u_dirty_rat;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;
import junit.framework.TestCase;

import edu.gatech.jjmae.u_dirty_rat.controller.ExistingUserLoginActivity;
import edu.gatech.jjmae.u_dirty_rat.model.User;

/**
 * Created by Elizabeth on 11/20/2017.
 * tests for the function isPasswordValid within ExistingUserLoginActivity
 */

public class IsPasswordValidTest {
    /*
    method that tests functionality of isPasswordValid
     */
    @Test
    public void testPassValid() {

        ExistingUserLoginActivity act = new ExistingUserLoginActivity();

        String pass1 = "abc";
        String pass2 = "asdfghjkl";
        String pass3 = "12o9";
        String pass4 = "u&op";
        String pass5 = "****";
        String pass6 = "rtyu-";
        String pass7 = ".89h";
        String pass8 = "jk";

        assertTrue(act.getIsPasswordValid(pass1));
        assertTrue(act.getIsPasswordValid(pass2));
        assertTrue(act.getIsPasswordValid(pass3));
        assertFalse(act.getIsPasswordValid(pass4));
        assertFalse(act.getIsPasswordValid(pass5));
        assertFalse(act.getIsPasswordValid(pass6));
        assertFalse(act.getIsPasswordValid(pass7));
        assertFalse(act.getIsPasswordValid(pass8));
    }
}