package com.example.destructor.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

public class MyDbManager {
    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public MyDbManager(Context context) {
        this.context = context;
        myDbHelper = new MyDbHelper(context);

    }

    public void isertToDb(Data data){
        db = myDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ScoreTable.TITLE,data.pos);
        cv.put(ScoreTable.POINTS,data.points);
        db.insert(ScoreTable.TABLE_NAME, null, cv);
        db.close();
    }

    public LinkedList<Data> getFromDb(){

        LinkedList<Data> list = new LinkedList<>();
        SQLiteDatabase db = myDbHelper.getWritableDatabase();

        Cursor cursor = db.query(ScoreTable.TABLE_NAME,null, null, null,null,null,null);

        if(cursor.moveToFirst()){
            do {
                int id_n = cursor.getColumnIndex(ScoreTable.TITLE);
                int id_p = cursor.getColumnIndex(ScoreTable.POINTS);

                Data data = new Data(cursor.getInt(id_n),cursor.getInt(id_p));
                list.add(data);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return list;
    }
}
