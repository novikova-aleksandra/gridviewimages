package com.twogis.gridviewimages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class GridViewFragment extends Fragment {

    public static GridViewFragment newInstance() {
        return new GridViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final BackgroundActivity activity = (BackgroundActivity) getActivity();
        final GridViewAdapter adapter = new GridViewAdapter(activity, activity);

        GridView gridView = (GridView) LayoutInflater.from(activity)
                .inflate(R.layout.gridview_layout, container, false);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = adapter.getItem(position);
                activity.showFullImage(url);
            }
        });

        return gridView;
    }
}
