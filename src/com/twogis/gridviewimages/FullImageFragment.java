package com.twogis.gridviewimages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class FullImageFragment extends Fragment {

    private static final String URL_KEY = "url";

    public static FullImageFragment newInstance(String url) {
        Bundle arguments = new Bundle();
        arguments.putString(URL_KEY, url);

        FullImageFragment fragment = new FullImageFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BackgroundActivity activity = (BackgroundActivity) getActivity();
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.fullimage_layout, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.fullimage);
        Bundle arguments = getArguments();
        String url = arguments.getString(URL_KEY);
        Picasso.with(activity)
                .load(url)
                .into(imageView);
        return view;
    }
}
