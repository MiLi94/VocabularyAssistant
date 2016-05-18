package com.limi.andorid.vocabularyassistant.data;

/**
 * Created by limi on 16/3/22.
 */
public class UserAccount {

    private String name;
    private String email;
    private int userId;


    public UserAccount(int id, String email, String name) {
        this.name = name;
        this.email = email;
        userId = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
}
