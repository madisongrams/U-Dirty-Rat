package edu.gatech.jjmae.u_dirty_rat;

/**
 * Created by Madison on 9/29/2017.
 */

public abstract class AbstractUser {
    private String username;

    public AbstractUser(String username) {
        this.username = username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
