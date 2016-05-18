package com.limi.andorid.vocabularyassistant.data;

/**
 * Created by limi on 16/5/18.
 */
public class Record {

    private int userID;
    private int lastCount;
    private int thisCount;
    private int todayRank;
    private int lastRank;
    private String update_date;
    private String userName;

    public Record(int userID, int thisCount, int lastCount) {
        this.thisCount = thisCount;
        this.userID = userID;
        this.lastCount = lastCount;
    }

    public Record(int userID, int lastCount, int thisCount, int todayRank, int lastRank, String update_date) {
        this.userID = userID;
        this.lastCount = lastCount;
        this.thisCount = thisCount;
        this.todayRank = todayRank;
        this.lastRank = lastRank;
        this.update_date = update_date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getLastCount() {
        return lastCount;
    }

    public void setLastCount(int lastCount) {
        this.lastCount = lastCount;
    }

    public int getThisCount() {
        return thisCount;
    }

    public void setThisCount(int thisCount) {
        this.thisCount = thisCount;
    }

    public int getTodayRank() {
        return todayRank;
    }

    public void setTodayRank(int todayRank) {
        this.todayRank = todayRank;
    }

    public int getLastRank() {
        return lastRank;
    }

    public void setLastRank(int lastRank) {
        this.lastRank = lastRank;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    @Override
    public String toString() {
        return "Record{" +
                "userID=" + userID +
                ", lastCount=" + lastCount +
                ", thisCount=" + thisCount +
                ", todayRank=" + todayRank +
                ", lastRank=" + lastRank +
                ", update_date='" + update_date + '\'' +
                '}';
    }
}
