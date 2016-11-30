package com.example.serpentcs.recycdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.serpentcs.recycdemo.R;

/**
 * Created by serpentcs on 17/11/16.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    TextView title;


    public ItemViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.thumbnail);
        title = (TextView) itemView.findViewById(R.id.title);


    }
}
