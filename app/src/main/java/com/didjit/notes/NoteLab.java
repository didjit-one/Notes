package com.didjit.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.didjit.notes.database.NoteBaseHelper;
import com.didjit.notes.database.NoteCursorWrapper;
import com.didjit.notes.database.NoteDbSchema.NoteTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by didjit on 08.01.16.
 */
public class NoteLab {
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private static NoteLab sNoteLab;


    public static NoteLab get(Context context) {
        if (sNoteLab == null) {
            sNoteLab = new NoteLab(context);
        }
        return sNoteLab;
    }


    private NoteLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new NoteBaseHelper(mContext).getWritableDatabase();


    }

    public void addNote(Note n) {
        ContentValues values = getContentValues(n);
        mDatabase.insert(NoteTable.NAME, null, values);

    }

    public void deleteNote(UUID noteId) {

String uuidString=noteId.toString();
        mDatabase.delete(NoteTable.NAME,NoteTable.Cols.UUID+"=?",new String[]{uuidString});

    }


    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<>();
        NoteCursorWrapper cursor = queryNotes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return notes;

    }

    public Note getNote(UUID id) {
        NoteCursorWrapper cursor = queryNotes(NoteTable.Cols.UUID + " = ?", new String[]{id.toString()});
        try {
            if (cursor.getCount()==0){
                return null;
            } cursor.moveToFirst();
            return cursor.getNote();
        } finally {
            cursor.close();
        }
    }

    public void updateNote(Note note) {
        String uuidString = note.getId().toString();
        ContentValues values = getContentValues(note);
        mDatabase.update(NoteTable.NAME, values, NoteTable.Cols.UUID + " = ?", new String[]{uuidString});
    }


    private static ContentValues getContentValues(Note note) {
        ContentValues values = new ContentValues();
        values.put(NoteTable.Cols.UUID, note.getId().toString());
        values.put(NoteTable.Cols.TITLE, note.getTitle());
        values.put(NoteTable.Cols.DATE, note.getDate().getTime());
        values.put(NoteTable.Cols.DONE, note.isDone() ? 1 : 0);
        return values;
    }

    private NoteCursorWrapper queryNotes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(NoteTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new NoteCursorWrapper(cursor);
    }

}
