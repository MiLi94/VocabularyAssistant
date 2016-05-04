package com.limi.andorid.vocabularyassistant.helper;

/**
 * Created by limi ID 1302197 on 16/4/26.
 */
public class Word {

    private static int lastID;
    private final String word;
    private final String trans;
    private final String tags;
    private final String phonetic;
    private final String wordBase;
    private int ID;

    public Word(String word, String trans, String tags, String wordbase, String phonetic) {
        this.word = word;
        this.trans = trans;
        this.tags = tags;
        this.wordBase = wordbase;
        this.ID = lastID;
        this.phonetic = phonetic;
        lastID++;
    }

    public static int getLastID() {
        return lastID;
    }

    public static void setLastID(int lastID) {
        Word.lastID = lastID;
    }

    public String getWord() {
        return word;
    }

    public String getTrans() {
        return trans;
    }

    public String getTags() {
        return tags;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getWordBase() {
        return wordBase;
    }

    @Override
    public String toString() {
        return word;
    }
}
