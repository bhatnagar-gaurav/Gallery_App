package com.practice.test.gallleryapp.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by gaurav_bhatnagar on 3/6/2016.
 * This is the Basic DataBean used across the entire application.
 * It has all the required Getter/Setter Methods and also It has implemented the parcelable Interface
 * so that it could be easily transferred across different components in the application.
 */
public class Images extends BasicResponse implements Parcelable {
    public int albumId;
    public int id;
    public String title;
    public String url;
    public String thumbnailUrl;

    public Images(int albumId, int id, String title, String url, String thumbnailUrl) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    // Methods for Implementing Parcelable Interface
    public static final Parcelable.Creator<Images> CREATOR =
            new Parcelable.Creator<Images>() {
                public Images createFromParcel(Parcel in) {
                    return new Images(in);
                }
                public Images[] newArray(int size){
                    return new Images[size];
                }
            };

    // Methods for Implementing Parcelable Interface.
    private Images(Parcel in){
        this.albumId = in.readInt();
        this.id = in.readInt();
        this.thumbnailUrl = in.readString();
        this.title = in.readString();
        this.url = in.readString();
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }


    // Methods for Implementing the Parcelable Interface.
    @Override
    public int describeContents() {
        return this.hashCode();
    }

    // Methods for Implementing the Parcelable Interface.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(getAlbumId());
        out.writeInt(getId());
        out.writeString(getThumbnailUrl());
        out.writeString(getTitle());
        out.writeString(getUrl());
    }
}
