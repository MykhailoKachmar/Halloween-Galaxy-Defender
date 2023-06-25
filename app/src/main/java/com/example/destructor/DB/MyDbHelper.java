package com.example.destructor.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper {

    public MyDbHelper(@Nullable Context context) {
        super(context, ScoreTable.DB_NAME, null, ScoreTable.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(ScoreTable.TABLE_STRUCTURE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(ScoreTable.DROP_TABLE);
        onCreate(db);
    }

}
