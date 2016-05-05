package com.limi.andorid.vocabularyassistant.helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by limi on 16/5/4.
 */
public class UserWordSQLiteHandler extends SQLiteOpenHelper {


    private static final String TAG = LoginSQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android";

    // Login table name
    private static final String TABLE_USER = "userWord";

    // Login Table Columns names
    private static final String KEY_ID = "userID";
    private static final String KEY_NAME = "wordID";
    private static final String KEY_CREATED_AT = "created_at";

    public UserWordSQLiteHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public UserWordSQLiteHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
