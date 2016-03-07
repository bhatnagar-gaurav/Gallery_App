package com.practice.test.gallleryapp.userinterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.practice.test.gallleryapp.GalleryApplication;
import com.practice.test.gallleryapp.R;

    /* This method needs to create, configure and return a ViewHolder (in this case the object for RowHolder Class)
    *  for a particular row in the RecyclerView
    *  @param : ViewGroup - This will hold the views managed by the RowHolder mostly for use with LayoutInflation
    *  @param : viewType - An int value used particularly when we are using multipleViewTypes for populating different
    *  rows in RecyclerView. */

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView=null;


    public void setAdapter(RecyclerView.Adapter adapter) {
        getRecyclerView().setAdapter(adapter);
    }

    public RecyclerView.Adapter getAdapter() {
        return(getRecyclerView().getAdapter());
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        getRecyclerView().setLayoutManager(manager);
    }

    public RecyclerView getRecyclerView() {
        if (recyclerView==null) {
            recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        }
        return(recyclerView);
    }
}
