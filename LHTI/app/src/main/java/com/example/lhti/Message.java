package com.example.lhti;

import java.sql.Timestamp;
import java.util.Date;

public class Message {

    String title;
    String contents;

    Date time;


    public Message()
    {

    }
    public Message(String title, String contents, Timestamp time) {
        this.title = title;
        this.contents = contents;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
