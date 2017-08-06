package com.example.heber.w3d5_ex01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.heber.w3d5_ex01.FeedReaderContract.FeedEntry;

/**
 * Created by heber on 8/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mydatabase.db";

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" + FeedEntry._ID + " INTEGER PRIMARY KEY,"
            + FeedEntry.COLUMN_GENDER + " TEXT," + FeedEntry.COLUMN_NAME + " TEXT," + FeedEntry.COLUMN_ADDRESS + " TEXT,"
            + FeedEntry.COLUMN_EMAIL + " TEXT," + FeedEntry.COLUMN_DOB + " TEXT," + FeedEntry.COLUMN_PHONE + " TEXT," + FeedEntry.COLUMN_CELL + " TEXT,"
            + FeedEntry.COLUMN_PICTURE_PATH + " TEXT," + FeedEntry.COLUMN_NATIONALITY + " TEXT)";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
