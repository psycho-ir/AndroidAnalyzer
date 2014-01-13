package com.sarabadani.android.analyzer.model;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by soroosh on 1/13/14.
 */
public class Contact {
    public final static Contact NullContact = new Contact("",null,null);
    private final String name;

    private final Uri thumbnalPhoto;

    private final Bitmap photo;
    public Contact(String name,Uri thumbnalPhoto,Bitmap photo){

        this.name = name;
        this.thumbnalPhoto = thumbnalPhoto;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public Uri getThumbnalPhoto() {
        return thumbnalPhoto;
    }

    public Bitmap getPhoto() {
        return photo;
    }
}
