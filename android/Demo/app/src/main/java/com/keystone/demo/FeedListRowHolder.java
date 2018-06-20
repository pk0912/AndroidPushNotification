package com.keystone.demo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class FeedListRowHolder extends RecyclerView.ViewHolder {
    /*protected ImageView thumbnail;*/
    protected TextView title,data;

    public FeedListRowHolder(View view) {
        super(view);
/*
        this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
*/
        //this.title = (TextView) view.findViewById(R.id.title);
        this.data = (TextView) view.findViewById(R.id.code);
    }

}