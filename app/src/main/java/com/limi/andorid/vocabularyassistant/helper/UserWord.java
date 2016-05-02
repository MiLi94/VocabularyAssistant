package com.limi.andorid.vocabularyassistant.helper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by limi on 16/4/27.
 */

public class UserWord {
    public static ArrayList<UserWord> userWordArrayList = new ArrayList<>();
    private int wordID;
    private int userID;
    private int wrongTime = 0;
    private Date date;
    private boolean isFavourite = false;
    private String wordBase;


    public UserWord(int wordID, int userID, String wordBase) {
        this.wordID = wordID;
        this.userID = userID;
        this.wordBase = wordBase;
        date = new Date();
        userWordArrayList.add(this);
    }

//    public static void addList(UserWord word){
//        userWordArrayList.add(word);
//    }

    public void setFavourite() {
        isFavourite = !isFavourite;
    }

    public ArrayList<UserWord> getUserWordArrayList() {
        return userWordArrayList;
    }

    public void inWrongTime() {
        wrongTime++;
    }

    public void deWrongTime() {
        wrongTime--;
    }

    public int getWordID() {
        return wordID;
    }

    public void setWordID(int wordID) {
        this.wordID = wordID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getWrongTime() {
        return wrongTime;
    }

    public void setWrongTime(int wrongTime) {
        this.wrongTime = wrongTime;
    }

    public void wrongTimeIncrease() {
        wrongTime++;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getWordBase() {
        return wordBase;
    }

    public void setWordBase(String wordBase) {
        this.wordBase = wordBase;
    }

}
