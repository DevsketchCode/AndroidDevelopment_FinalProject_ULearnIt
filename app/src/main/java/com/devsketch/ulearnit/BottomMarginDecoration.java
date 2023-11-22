package com.devsketch.ulearnit;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class BottomMarginDecoration extends RecyclerView.ItemDecoration {

    private int bottomMargin;

    public BottomMarginDecoration(int bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();
        if (position == itemCount - 1) {
            outRect.bottom = bottomMargin;
        }
    }
}
