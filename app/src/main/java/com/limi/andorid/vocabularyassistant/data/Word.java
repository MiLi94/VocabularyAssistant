package com.limi.andorid.vocabularyassistant.data;

import com.limi.andorid.vocabularyassistant.helper.WordImportHandler;

import java.util.HashMap;

/**
 * Created by limi id 1302197 on 16/4/26.
 */
public class Word {

    private static int lastID;
    private static HashMap<String, Integer> idWordBase;
    private final String word;
    private final String trans;
    private final String tags;
    private final String phonetic;
    private final String wordBase;
    private int id;
    private int unit;
    private int list;

    public Word(String word, String trans, String tags, String wordbase, String phonetic) {
        this.word = word;
        this.trans = trans;
        this.tags = tags;
        this.wordBase = wordbase;
        this.id = lastID;
        this.phonetic = phonetic;
        lastID++;
        list = id / 100 + 1;
        unit = (id - (list - 1) * 100) / 10 + 1;

    }

    public static int getLastID() {
        return lastID;
    }

    public static void setLastID(int lastID) {
        Word.lastID = lastID;
    }

    public static void setLastID() {
        idWordBase.put(WordImportHandler.systemWordBaseArrayList.get(lastID - 1).getWordBase(), lastID - 1);
        lastID = (lastID / 1000) * 1000 + 1;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordBase() {
        return wordBase;
    }

    @Override
    public String toString() {
        return word;
    }
}