package com.example.serpentcs.recycdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.serpentcs.recycdemo.adapter.ItemAdapter;
import com.example.serpentcs.recycdemo.model.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ItemAdapter adapter;
    BufferedReader reader = null;
    String line;
    JSONArray ja;
    private List<Model> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        modelList = new ArrayList<>();
        adapter = new ItemAdapter(modelList, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        String file = "";
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("glide.json")));
            while ((line = reader.readLine()) != null) {
                file += line;
                Log.e(TAG, "onCreate: " + line);
            }
            ja = new JSONArray(file);


        } catch (Exception e) {
            e.printStackTrace();
        }
        prepareItems();
    }

    private void prepareItems() {
        try {
            for (int i = 0; i < ja.length(); i++) {
                JSONObject obj = ja.getJSONObject(i);
                JSONObject url = obj.getJSONObject("url");
                Model model = new Model(obj.getString("name"), url.getString("small"));
                modelList.add(model);
            }

//        model = new Model("Xscpae", imagess[1]);
//        modelList.add(model);
//        model = new Model("Maroon 5", imagess[2]);
//        modelList.add(model);
//
//        model = new Model("Born to Die", imagess[3]);
//        modelList.add(model);
//
//        model = new Model("Honeymoon", imagess[4]);
//        modelList.add(model);
//
//        model = new Model("I Need a Doctor", imagess[5]);
//        modelList.add(model);
//
//        model = new Model("Loud", imagess[6]);
//        modelList.add(model);
//
//        model = new Model("Legend", imagess[7]);
//        modelList.add(model);
//
//        model = new Model("Hello", imagess[8]);
//        modelList.add(model);
//
//        model = new Model("Greatest Hits", imagess[9]);
//        modelList.add(model);

            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public class pacingItemDecoration extends RecyclerView.ItemDecoration {
//
//        private int spanCount;
//        private int spacing;
//        private boolean includeEdge;
//
//        public pacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
//            this.spanCount = spanCount;
//            this.spacing = spacing;
//            this.includeEdge = includeEdge;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//
//            Log.d(TAG, "getItemOffsets() called with: outRect = ["
//                    + outRect + "], view = [" + view + "], parent = [" + parent + "], state = [" + state + "]");
//          int position= parent.getChildAdapterPosition(view);
//        }
//    }


}

