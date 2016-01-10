package com.didjit.notes.database;

/**
 * Created by didjit on 10.01.16.
 */
public class NoteDbSchema {
    public static final class NoteTable {
        public static final String NAME = "notes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String DONE = "done";
        }
    }
}
