package com.devsketch.ulearnit;

// Implement this in your Activity, and in these methods you would want to happen when the action is done
public interface RecyclerViewInterface {
    void onItemClick(int position);
    void onItemDoubleTap(int position);
    void onItemLongTap(int position);
}
