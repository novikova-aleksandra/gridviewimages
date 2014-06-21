package com.twogis.gridviewimages;

import android.content.Context;
import android.widget.ImageView;

public class SquaredImageView extends ImageView {

    public SquaredImageView (Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}