package com.practice.test.gallleryapp;

import android.app.Application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by gaurav_bhatnagar on 3/6/2016.
 */
public class GalleryApplication extends Application{
    private static final String TAG = "GalleryApplication";
    private static GalleryApplication application = null;
    private SharedPreferences preferences;
    private String prefsLaunch;
    private static Picasso picassoInstance = null;
    // STRING RELATD FOR SHARED PREFERENCES
    private static final String PREFS_LAUNCH  = "PREFS_FIRST_TIME";

    //Constructor
    public GalleryApplication() {

    }
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }
    /**
     *   Incorporating Image Caching for Picasso Library
     * @param context application Context
     */
    private Picasso customizePicassoCache (Context context) {

        Downloader downloader   = new OkHttpDownloader(context, Integer.MAX_VALUE);
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(downloader);
        picassoInstance = builder.build();
        picassoInstance.setLoggingEnabled(true);
        picassoInstance.setIndicatorsEnabled(true);
        //Also using singleton approach for Picasso instance.
        Picasso.setSingletonInstance(picassoInstance);
        return picassoInstance;
    }
    /**
     * Retrieve Singleton Picasso Instance
     * @param context application Context
     * @return Picasso instance
     */
    public Picasso getPicassoInstance (Context context) {

        if (picassoInstance == null) {
            picassoInstance = customizePicassoCache(context);
        }

        return picassoInstance;
    }

    public static GalleryApplication getInstance() {
        return application;
    }

    /**
     * Gets application version.
     * @return string represents application version.
     */
    public String getVersion() {
        PackageManager packageManager = getPackageManager();
        PackageInfo info = null;
        try {
            info = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.wtf(TAG, e);
        }
        return (info != null ? info.versionName : "");
    }
    // Keeping the check that the application is getting launched for the first time.
    public String getPrefsLaunch() {
        if(prefsLaunch == null) {
            this.prefsLaunch = preferences.getString(PREFS_LAUNCH, null);
        }
        return prefsLaunch;
    }


    public void setPrefsLaunch(String firstTime) {
        this.prefsLaunch = firstTime;
        SharedPreferences.Editor editor = preferences.edit();
        if (this.prefsLaunch != null){
            editor.putString(PREFS_LAUNCH,prefsLaunch);
        }
        editor.apply();
    }
}
