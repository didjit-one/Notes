package com.didjit.notes;

import java.util.Date;
import java.util.UUID;



/**
 * Created by didjit on 06.01.16.
 */
public class Note {
    private UUID mId;
    private String  mTitle;
    private Date mDate;
    private boolean mDone;


    public Note() {
       this(UUID.randomUUID());
    }


    public Note(UUID id) {
        mId = id;
        mDate=new Date();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
