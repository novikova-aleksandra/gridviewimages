package com.twogis.gridviewimages;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class GridViewAdapter extends BaseAdapter {

    private final Context context;
    private final BackgroundActivity activity;

    public GridViewAdapter (Context context, BackgroundActivity activity) {
        this.context = context;
        this.activity = activity;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if (view == null) {
            view = new SquaredImageView(context);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        String url = getItem(position);

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fit()
                .into(view);

        return view;
    }

    @Override
    public int getCount() {
        return activity.getUrls().size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public String getItem(int position) {
        return activity.getUrls().get(position);
    }

}
