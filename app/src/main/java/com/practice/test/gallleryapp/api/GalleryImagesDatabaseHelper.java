package com.practice.test.gallleryapp.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by gaurav_bhatnagar on 3/7/2016.
 * This Class is used for Creating/Upgrading the Database.
 */
public class GalleryImagesDatabaseHelper extends SQLiteOpenHelper{

    private static final String TAG = GalleryImagesDatabaseHelper.class.getSimpleName();
    public static final String TABLE_CREATION_QUERY = "create table "+ Constants.TABLE_NAME + " ( " + Constants.COLUMN_ID +
            " integer primary key , "+Constants.COLUMN_ALBUM_ID + " integer ," + Constants.COLUMN_THUMBNAIL_URL + " text ," + Constants.COLUMN_TITLE +
            " text , " + Constants.COLUMN_URL + " text);";

    public GalleryImagesDatabaseHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATION_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME + "/");
        onCreate(sqLiteDatabase);
    }
}
