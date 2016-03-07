package com.practice.test.gallleryapp.userinterface;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.test.gallleryapp.GalleryApplication;
import com.practice.test.gallleryapp.R;
import com.practice.test.gallleryapp.model.Images;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by gaurav_bhatnagar on 3/6/2016.
 * This Class is for rows in RecyclerView incorporating the View Holder Mechanism.
 * It is responsible for binding data as needed from the Data model into the widgets of each row.
 */
public class RowHolder extends RecyclerView.ViewHolder{
    private static final String TAG = RowHolder.class.getSimpleName();
    protected ImageView thumbnail;
    protected TextView title;
    View row;
    Picasso picassoInstance;
    GalleryApplication mApplication;


    public RowHolder(View row) {
        super(row);
        this.row = row;
        mApplication = GalleryApplication.getInstance();
        thumbnail = (ImageView)itemView.findViewById(R.id.thumbnail);
        title = (TextView)itemView.findViewById(R.id.image_title);

        /* ---------- To show Visual Impact on Clicks.
        As per the Material Design Rule, Setting up a ripple effect
        when the Touch event occurs for selecting
        ------------- any specific row in the Recycler View. */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            row.setOnTouchListener(new View.OnTouchListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    view.findViewById(R.id.image_row)
                            .getBackground()
                            .setHotspot(event.getX(), event.getY());

                    return(false);
                }
            });
        }
        picassoInstance = mApplication.getPicassoInstance(mApplication);
    }


    // This method takes the data-model and binds it to each row's widgets .
    void bindModel(final Images image, final GalleryAdapter.OnItemClickListener listener){

        // Lazy Loading the thumbnails For each Row
        picassoInstance.with(mApplication)
                .load(image.getThumbnailUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.mipmap.placeholder)
                .placeholder(R.mipmap.placeholder)
                .fit()
                .into(thumbnail, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        picassoInstance.with(mApplication)
                                .load(image.getThumbnailUrl())
                                .error(R.mipmap.placeholder)
                                .placeholder(R.mipmap.placeholder)
                                .fit()
                                .into(thumbnail, new Callback() {
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
        title.setText(image.getTitle());
        // Registering the OnClickListener Interface with Each Recycler Row.
        row.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                listener.onItemClick(image);
            }
        });
    }
}
