package com.sarabadani.android.analyzer.repository;

import android.content.Context;

/**
 * Created by soroosh on 1/13/14.
 */
public abstract class AbstractRepository {
    protected final Context context;

    public AbstractRepository(Context context){

        this.context = context;
    }
}
