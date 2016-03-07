package com.practice.test.gallleryapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import com.practice.test.gallleryapp.GalleryApplication;
import com.practice.test.gallleryapp.R;

/**
 * Created by gaurav_bhatnagar on 3/6/2016.
 */
public class Utils {

    static public final int NOT_EXISTS = -1;
    static private final String SHARED_PREFERENCES = "SETTINGS";

    public static void saveBoolValueForName(final String name, boolean value) {
        SharedPreferences settings = GalleryApplication.getInstance().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    public static boolean getBoolForName(final String name, boolean defaultValue) {
        SharedPreferences settings = GalleryApplication.getInstance().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return settings.getBoolean(name, defaultValue);
    }

    public static void showErrorDialog(Context context, String message) {

        if (context != null){
            new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.error_dialog_title))
                    .setMessage(message).setNegativeButton(context.getString(R.string.close),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) { }
                    }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else{
            GalleryApplication mApplication = GalleryApplication.getInstance();
            new AlertDialog.Builder(mApplication)
                    .setTitle(mApplication.getString(R.string.error_dialog_title))
                    .setMessage(message).setNegativeButton(mApplication.getString(R.string.close),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) { }
                    }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }
}
