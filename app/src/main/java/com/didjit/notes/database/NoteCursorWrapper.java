package com.didjit.notes.database;

/**
 * Created by didjit on 10.01.16.
 */
import android.database.Cursor;
import android.database.CursorWrapper;

import com.didjit.notes.Note;

import java.util.Date;
import java.util.UUID;


public class NoteCursorWrapper extends CursorWrapper {
    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote() {
        String uuidString = getString(getColumnIndex(NoteDbSchema.NoteTable.Cols.UUID));
        String title = getString(getColumnIndex(NoteDbSchema.NoteTable.Cols.TITLE));
        long date = getLong(getColumnIndex(NoteDbSchema.NoteTable.Cols.DATE));
        int isDone = getInt(getColumnIndex(NoteDbSchema.NoteTable.Cols.DONE));

        Note note = new Note(UUID.fromString(uuidString));
        note.setTitle(title);
        note.setDate(new Date(date));
        note.setDone(isDone != 0);

        return note;
    }
}
