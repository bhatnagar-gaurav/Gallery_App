package com.practice.test.gallleryapp.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.practice.test.gallleryapp.GalleryApplication;
import com.practice.test.gallleryapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


    /* This method needs to create, configure and return a ViewHolder (in this case the object for RowHolder Class)
    *  for a particular row in the RecyclerView
    *  @param : ViewGroup - This will hold the views managed by the RowHolder mostly for use with LayoutInflation
    *  @param : viewType - An int value used particularly when we are using multipleViewTypes for populating different
    *  rows in RecyclerView. */

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = DetailsActivity.class.getSimpleName();
    private GalleryApplication mApplication;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        mApplication = GalleryApplication.getInstance();
        imageView = (ImageView)findViewById(R.id.details_imageView);
        //Getting Intent Data
        Intent intent = getIntent();
        final Picasso picasso = mApplication.getPicassoInstance(getApplicationContext());
        final String imageUrl = intent.getStringExtra(getString(R.string.tag_url));

        picasso.with(mApplication)
                .load(imageUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .resize(600, 600)
                .error(R.mipmap.placeholder)
                .placeholder(R.mipmap.placeholder)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        picasso.with(mApplication)
                                .load(imageUrl)
                                .error(R.mipmap.placeholder)
                                .placeholder(R.mipmap.placeholder)
                                .resize(600, 600)
                                .into(imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }
                                    @Override
                                    public void onError() {
                                        Log.v(TAG, "Could not fetch image");
                                    }
                                });
                    }
                });
    }

}
