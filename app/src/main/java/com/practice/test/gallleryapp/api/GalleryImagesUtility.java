package com.practice.test.gallleryapp.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.practice.test.gallleryapp.model.Images;

import java.util.ArrayList;

/**
 * Created by gaurav_bhatnagar on 3/7/2016.
 */
public class GalleryImagesUtility {
    private GalleryImagesDatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context mContext;
    public GalleryImagesUtility(Context context) {
        mContext = context;
        databaseHelper = new GalleryImagesDatabaseHelper(mContext);
    }

    public void open(){
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void openReadable(){
        sqLiteDatabase = databaseHelper.getReadableDatabase();
    }
    public void close(){
        sqLiteDatabase.close();
    }

    public void addImage(Images image){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.COLUMN_ID,image.getId());
        contentValues.put(Constants.COLUMN_ALBUM_ID,image.getAlbumId());
        contentValues.put(Constants.COLUMN_THUMBNAIL_URL,image.getThumbnailUrl());
        contentValues.put(Constants.COLUMN_TITLE,image.getTitle());
        contentValues.put(Constants.COLUMN_URL, image.getUrl());
        long id = sqLiteDatabase.insert(Constants.TABLE_NAME,null,contentValues);
    }

    public ArrayList<Images> getAllImages(){
        ArrayList<Images> thumbnailList = new ArrayList<Images>();
        openReadable();
        String[] columns = {Constants.COLUMN_ID,Constants.COLUMN_ALBUM_ID,Constants.COLUMN_THUMBNAIL_URL,Constants.COLUMN_TITLE,Constants.COLUMN_URL};
        Cursor cursor = sqLiteDatabase.query(Constants.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null ){
            if (cursor.moveToFirst()){
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_ID));
                    int albumId = cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_ALBUM_ID));
                    String thumbnailURL = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_THUMBNAIL_URL));
                    String title = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TITLE));
                    String url = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_URL));
                    Images image = new Images(albumId,id,title,url,thumbnailURL);
                    thumbnailList.add(image);
                }while(cursor.moveToNext());

            }
        }
        if (null != cursor){
            cursor.close();
        }
        close();
        return thumbnailList;
    }
}
