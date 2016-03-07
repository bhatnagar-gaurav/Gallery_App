package com.practice.test.gallleryapp.api;

/**
 * Created by gaurav_bhatnagar on 3/6/2016.
 */
public class Constants {

    /**
     * URL related strings
     */
    public static final String URL_LOADING_IMAGES = "http://jsonplaceholder.typicode.com/photos";
    public static final String DB_NAME = "images.db";
    public static final String TABLE_NAME = "galleryImages";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ALBUM_ID = "albumId";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_THUMBNAIL_URL = "thumbnailUrl";
    public static final int DB_VERSION = 1;

    /**
     *Actions
     */
    public static final int ACTION_LOAD_IMAGES = 210;
}
