package com.cormacx.electroluxexam;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingDecoration extends RecyclerView.ItemDecoration {

    public Integer padding;

    public SpacingDecoration( Integer pixelDensity ) {
        padding = pixelDensity;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = padding;
    }
}
