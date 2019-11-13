package com.example.myproject_is1208.question;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myproject_is1208.MainActivity;

public class MyDBHelper extends SQLiteOpenHelper {
    public int DB_VERSION = 5;


    String CREATE_TABLE_Question = "CREATE TABLE Question (" +
            "_id int PRIMARY KEY," +
            "question nvarchar(255)," +
            "ans_a nvarchar(255)," +
            "ans_b nvarchar(255)," +
            "ans_c nvarchar(255)," +
            "ans_d nvarchar(255)," +
            "result nvarchar(255)," +
            "num_exam int," +
            "subject nvarchar(255)," +
            "image nvarchar(255))";

    public String CREATE_TABLE_SCORE = "CREATE TABLE Score (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name nvarchar(255)," +
            "room nvarchar(255)," +
            "score int," +
            "date DATETIME DEFAULT CURRENT_DATE)";

    String INSERT_INTO_SCORE = "INSERT INTO Score ( name,room , score)\n" +
            "VALUES (?,?,?)";

    String INSERT_INTO_QUESTION = "INSERT INTO Question (_id, question, ans_a, ans_b , ans_c , ans_d , result , num_exam , subject , image)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?)";
    public MyDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory,version);
    }

    public Cursor selectData(String sql){
        SQLiteDatabase readableDatabase = getReadableDatabase();
        return readableDatabase.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_Question);
        sqLiteDatabase.execSQL(CREATE_TABLE_SCORE);
        sqLiteDatabase.execSQL(INSERT_INTO_QUESTION, new Object[]{0, "1 + 1 = ", "1", "2" , "3", "4" , "B" , 1 , "Math" , "img_1"});
        sqLiteDatabase.execSQL(INSERT_INTO_QUESTION, new Object[]{1, "2 + 4 = ","5", "1" , "6" , "0" , "C" , 2 , "Math" , "img_1"});
        sqLiteDatabase.execSQL(INSERT_INTO_QUESTION, new Object[]{2, "1 + 7 = ", "3", "8" , "7", "1" , "C" , 1 , "Math" , "img_1"});
        sqLiteDatabase.execSQL(INSERT_INTO_QUESTION, new Object[]{3, "1 + 7 = ", "3", "8" , "7", "1" , "C" , 1 , "Physical" , "img_1"});
        for(int i = 4; i < 10; i ++){
            sqLiteDatabase.execSQL(INSERT_INTO_QUESTION,
                    new Object[]{i, "1 + 7 = ", "3", "8" , "7", "1" , "C" , 1 , "Math" , "icon_1.png"});
        }
        for(int i = 11; i < 26; i ++){
            sqLiteDatabase.execSQL(INSERT_INTO_QUESTION,
                    new Object[]{i, "1 + 7 = ", "3", "8" , "7", "1" , "C" , 1 , "Physical" , "img_1"});
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE_BOOK = "DROP TABLE IF EXISTS Question";
        String DROP_TABLE_SCORE = "DROP TABLE IF EXISTS Score";
        if(i != i1){
            sqLiteDatabase.execSQL(DROP_TABLE_BOOK);
            sqLiteDatabase.execSQL(DROP_TABLE_SCORE);
            onCreate(sqLiteDatabase);
        }
    }

}
