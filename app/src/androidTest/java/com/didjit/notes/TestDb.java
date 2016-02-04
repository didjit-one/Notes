package com.didjit.notes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.didjit.notes.database.NoteBaseHelper;
import com.didjit.notes.database.NoteDbSchema;

import java.util.HashSet;

/**
 * Created by didjit on 31.01.16.
 */
public class TestDb extends AndroidTestCase{
    public static final String LOG_TAG=TestDb.class.getSimpleName();
    void deleteTheDatabase(){
        mContext.deleteDatabase(NoteBaseHelper.DATABASE_NAME);
    }

    public void setUp(){
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable{

        final HashSet<String> tableNameHashSet=new HashSet<String>();
        tableNameHashSet.add(NoteDbSchema.NoteTable.NAME);
        mContext.deleteDatabase(NoteBaseHelper.DATABASE_NAME);
        SQLiteDatabase db=new NoteBaseHelper(this.mContext).getWritableDatabase();
        assertEquals(true,db.isOpen());
        Cursor c=db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",null);
        assertTrue("ERROR:This means that the database has not been created correctly",c.moveToFirst());

    }




}
