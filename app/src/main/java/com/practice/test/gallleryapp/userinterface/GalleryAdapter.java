package com.practice.test.gallleryapp.userinterface;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.test.gallleryapp.R;
import com.practice.test.gallleryapp.model.Images;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gaurav_bhatnagar on 3/6/2016.
 *
 * This Class and the Row-holder class are collectively
 * helpful to convert the Data-Model into Visual Representation
 *
 * In order to Incorporate Click Events In the Recycler View
 * I have used the mechanism described in the following blog.
 * References : http://antonioleiva.com/recyclerview-listener/
 *
 */
public class GalleryAdapter extends RecyclerView.Adapter<RowHolder>{


    // Interface Used for Capturing the Click Event on the RecyclerView.
    public interface OnItemClickListener {
        void onItemClick(Images item);
    }
    private Context context;
    private final OnItemClickListener itemClickListener;
    private ArrayList<Images> thumbnailList;
    public GalleryAdapter(Context context,ArrayList<Images> imagesList,OnItemClickListener listener) {
        this.context = context;
        thumbnailList = imagesList;
        this.itemClickListener = listener;
    }
    /* This method needs to create, configure and return a ViewHolder (in this case the object for RowHolder Class)
    *  for a particular row in the RecyclerView
    *  @param : ViewGroup - This will hold the views managed by the RowHolder mostly for use with LayoutInflation
    *  @param : viewType - An int value used particularly when we are using multipleViewTypes for populating different
    *  rows in RecyclerView. */
    @Override
    public RowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.row, parent,false);
        RowHolder rowHolder = new RowHolder(itemView);
        return rowHolder;
    }

    /* This method is responsible for updating a ViewHolder based on the data-model for a
     * a certain position */
    @Override
    public void onBindViewHolder(RowHolder holder, int position) {
        Images image = thumbnailList.get(position);

        // Custom BindModel Method used to capture the Click for the RecyclerView's Items
        // Passing the reference of the Interface to the Custom Bind Method of the Recycler View
        holder.bindModel(image,itemClickListener);
    }

    /* This Function has the same role as getCount() in ListAdapter
     It Indicates how many items/rows there will be in RecyclerView.*/
    @Override
    public int getItemCount() {
        return thumbnailList.size();
    }
}
