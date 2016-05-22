package com.limi.andorid.vocabularyassistant.data;

/**
 * Created by limi on 16/5/18.
 */
public class NotificationEntry {

    private String content;
    private String date;

    public NotificationEntry(String content, String date) {
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
