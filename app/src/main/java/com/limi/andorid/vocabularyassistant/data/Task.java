package com.limi.andorid.vocabularyassistant.data;

/**
 * Created by limi on 16/5/10.
 */
public class Task {

    private int startList;
    private int endList;
    private int startUnit;
    private int endUnit;
    private int bookId;

    public Task(int startList, int endList, int startUnit, int endUnit, int bookId) {
        this.startList = startList;
        this.endList = endList;
        this.startUnit = startUnit;
        this.endUnit = endUnit;
        this.bookId = bookId;
    }

    public int getStartList() {
        return startList;
    }

    public void setStartList(int startList) {
        this.startList = startList;
    }

    public int getEndList() {
        return endList;
    }

    public void setEndList(int endList) {
        this.endList = endList;
    }

    public int getStartUnit() {
        return startUnit;
    }

    public void setStartUnit(int startUnit) {
        this.startUnit = startUnit;
    }

    public int getEndUnit() {
        return endUnit;
    }

    public void setEndUnit(int endUnit) {
        this.endUnit = endUnit;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
