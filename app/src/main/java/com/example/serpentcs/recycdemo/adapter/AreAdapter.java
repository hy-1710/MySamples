package com.example.serpentcs.recycdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.serpentcs.recycdemo.R;
import com.example.serpentcs.recycdemo.model.AreaViewHolder;

import java.util.List;

/**
 * Created by serpentcs on 25/11/16.
 */

public class AreAdapter extends RecyclerView.Adapter<AreaViewHolder> {

    private Context context;
    private List<String> deliver_type;

    public AreAdapter(Context context, List<String> deliver_type) {
        this.context = context;
        this.deliver_type = deliver_type;
    }

    @Override
    public AreaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new AreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AreaViewHolder holder, int position) {

        holder.tv_area_list.setText(deliver_type.get(position));

    }

    @Override
    public int getItemCount() {
        return deliver_type.size();
    }
}
