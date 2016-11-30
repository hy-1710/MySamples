package com.example.serpentcs.recycdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.serpentcs.recycdemo.R;
import com.example.serpentcs.recycdemo.model.Model;

import java.util.List;

/**
 * Created by serpentcs on 17/11/16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private List<Model> modelList;
    private Context context;

    public ItemAdapter(List<Model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //alwys view create and inflate layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        //setText or set the view
        Model model = modelList.get(position);
        holder.title.setText(model.getName());
        Glide.with(context).load(model.getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
