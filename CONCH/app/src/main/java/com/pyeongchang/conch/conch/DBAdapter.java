package com.pyeongchang.conch.conch;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hyojung on 2017-07-13.
 */
public class DBAdapter {

    public static final String DATABASE_NAME = "conch";

    public static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_POST =
            "CREATE TABLE posts (post_id integer primary key autoincrement, "
                    + PostDBAdapter.USER_ID+ " integer,"
                    + PostDBAdapter.CONTENT+ " TEXT);";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        this.DBHelper = new DatabaseHelper(this.context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_TABLE_POST);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            // Adding any table mods to this guy here
        }
    }
    public DBAdapter open() throws SQLException
    {
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        this.DBHelper.close();
    }
}