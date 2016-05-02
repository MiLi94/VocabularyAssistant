package com.limi.andorid.vocabularyassistant.helper;

/**
 * Created by limi on 16/3/22.
 */
public class UserAccount {

    private String fullName;
    private String email;
    private int userId;


    public UserAccount(int id, String email, String fullName) {
        this.fullName = fullName;
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

    //    public static boolean match(String email, String password) {
//        Iterator<UserAccount> iterator;
//        iterator = userAccountArrayList.listIterator();
//        while (iterator.hasNext()) {
//            UserAccount ac = iterator.next();
//            if (ac.email.equalsIgnoreCase(email) && ac.password.equals(password)) {
//                return true;
//            }
//
//        }
//        return false;
//    }

//    public ArrayList<UserAccount> getAccoutList() {
//        return userAccountArrayList;
//
//    }
}
