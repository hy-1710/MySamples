package com.example.serpentcs.recycdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.serpentcs.recycdemo.R;
import com.example.serpentcs.recycdemo.model.SerModel;

import java.util.List;

/**
 * Created by serpentcs on 29/11/16.
 */

public class ServiceAdapter extends ArrayAdapter<SerModel> {
    public static final String TAG = "ServiceAdapter";
    private Context context;
    private List<SerModel> area_list;


    public ServiceAdapter(Context context, List<SerModel> area_list) {
        super(context, 0, area_list);
        this.context = context;
        this.area_list = area_list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SerModel serModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_area, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView id = (TextView) convertView.findViewById(R.id.tv_id);
        name.setText(serModel.getName());
        Log.e(TAG, "getView: NAME OF TV" + name);
        id.setText(serModel.getId());
        return convertView;
    }
}
