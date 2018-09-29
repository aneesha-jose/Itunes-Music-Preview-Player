package com.mplayer.itunesmusicpreviewplayer.common.view;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

public class RecyclerViewTouchDisabler implements RecyclerView.OnItemTouchListener {

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return true;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean t) {

    }
}
