package com.example.destructor.DB;

public class ScoreTable {
    public static final String TABLE_NAME = "table";
    public static final String _ID = "_ID";
    public static final String TITLE = "title";
    public static final String POINTS = "points";
    public static final String DB_NAME = "score_db.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_STRUCTURE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," +  TITLE + " INTEGER," +POINTS + " INTEGER)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
