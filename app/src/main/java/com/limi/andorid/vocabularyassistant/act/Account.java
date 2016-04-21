package com.limi.andorid.vocabularyassistant.act;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by limi on 16/3/22.
 */
public class Account {

    private static int lastID = 0;
    private static ArrayList<Account> accountArrayList;
    private String email;
    private String password;
    private int id;


    public Account(String email, String password) {
        this.email = email;
        this.password = password;
        id = ++lastID;
        accountArrayList.add(this);
    }

    public static boolean match(String email, String password) {
        Iterator<Account> iterator;
        iterator = accountArrayList.listIterator();
        while (iterator.hasNext()) {
            Account ac = iterator.next();
            if (ac.email.equalsIgnoreCase(email) && ac.password.equals(password)) {
                return true;
            }

        }
        return false;
    }

    public ArrayList<Account> getAccoutList() {
        return accountArrayList;

    }
}
