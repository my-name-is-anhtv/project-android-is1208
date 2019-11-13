package com.example.myproject_is1208.score;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myproject_is1208.question.MyDBHelper;


public class ScoreController {
    private MyDBHelper dbHelper;

    public ScoreController(Context context) {
        dbHelper= new MyDBHelper(context,"a.db" , null , 5);
    }

    public void insertScore(String name, int score, String room){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("score", score);
        values.put("room", room);
        db.insert("Score", null, values);
        db.close();
    }


    //Lấy danh sách điểm
    public Cursor getScore() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Score", //ten bang
                null, //danh sach cot can lay
                null, //dieu kien where
                null, //doi so dieu kien where
                null, //bieu thuc groupby
                null, //bieu thuc having
                "_id DESC", //bieu thuc orderby
                null
        );
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor getSearchQuestion(String subject, String key) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Question WHERE question LIKE '%"+key+"%' AND subject LIKE '%"+subject+"%'",null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //lay danh sach cau hoi theo mon hoc
    public Cursor getQuestionBySubject(String key) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Question WHERE subject like '%"+key+"%'",null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
