package com.example.serpentcs.recycdemo.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.serpentcs.recycdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by serpentcs on 25/11/16.
 */

public class AreaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_area_list)
    public
    TextView tv_area_list;

    public AreaViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
