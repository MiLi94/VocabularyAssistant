package com.limi.andorid.vocabularyassistant.helper;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by limi on 16/4/27.
 */

public class UserWord {
    public static LinkedHashMap<Integer, UserWord> userWordHashMap = new LinkedHashMap<>();
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

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserWord userWord = (UserWord) o;

        if (wordID != userWord.wordID) return false;
        if (userID != userWord.userID) return false;
        return !(wordBase != null ? !wordBase.equals(userWord.wordBase) : userWord.wordBase != null);

    }

    @Override
    public int hashCode() {
        int result = wordID;
        result = 31 * result + userID;
        result = 31 * result + (wordBase != null ? wordBase.hashCode() : 0);
        return result;
    }
//    public static void addList(UserWord word){
//        userWordHashMap.add(word);
//    }

    public void setFavourite() {
        isFavourite = !isFavourite;
    }

    public LinkedHashMap<Integer, UserWord> getUserWordHashMap() {
        return userWordHashMap;
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
