package com.pyeongchang.conch.conch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by hyojung on 2017-07-13.
 */

public class PostDBAdapter {

    public static final String USER_ID = "user_id";
    public static final String POST_ID = "post_id";
    public static final String CONTENT= "content";

    private static final String DATABASE_TABLE = "posts";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DBAdapter.DATABASE_NAME, null, DBAdapter.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    public PostDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public PostDBAdapter open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

    /**
     * close return type: void
     */
    public void close() {
        this.mDbHelper.close();
    }

    public long createPost(String userId, String content, String postId){
        ContentValues initialValues = new ContentValues();
        initialValues.put(USER_ID, userId);
        initialValues.put(POST_ID, postId);
        initialValues.put(CONTENT, content);
        return this.mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deletePost(long postId) {

        return this.mDb.delete(DATABASE_TABLE, POST_ID + "=" + postId, null) > 0;
    }

    public ArrayList getAllPost() {

        Cursor res =  this.mDb.query(DATABASE_TABLE, new String[] { POST_ID,
                USER_ID, CONTENT}, null, null, null, null, null);
        ArrayList<String> array_list = new ArrayList<>();
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString((res.getColumnIndex(POST_ID))));
            res.moveToNext();
        }
        return array_list;
    }

    public Cursor getPost(long postId) throws SQLException {

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, new String[] { POST_ID, USER_ID,
                        CONTENT}, POST_ID + "=" + postId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateCar(String userId, String content, String postId){
        ContentValues args = new ContentValues();
        args.put(USER_ID, userId);
        args.put(POST_ID, postId);
        args.put(CONTENT, content);

        return this.mDb.update(DATABASE_TABLE, args, POST_ID + "=" + postId, null) >0;
    }
}