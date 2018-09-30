package com.mplayer.itunesmusicpreviewplayer.common.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

public class DrawablePagerIndicatorDecoration extends RecyclerView.ItemDecoration {

    private static final float DP = Resources.getSystem().getDisplayMetrics().density;

    /**
     * Height of the space the indicator takes up at the bottom of the view.
     */
    private final int mIndicatorHeight;

    /**
     * Indicator width.
     */
    private final float mIndicatorItemLength;

    /**
     * Padding between indicators.
     */
    private final float mIndicatorItemPadding = DP * 4;

    /**
     * Padding between indicators.
     */
    private final float mIndicatorItemMargin = DP * 16;

    /**
     * Some more natural animation interpolation
     */
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private final Paint mPaint = new Paint();

    private Drawable mPagerIndicatorSelected;
    private Drawable mPagerIndicatorUnselected;

    public DrawablePagerIndicatorDecoration(Drawable pagerIndicatorSelected, Drawable pagerIndicatorUnselected) {
        this.mPagerIndicatorSelected = pagerIndicatorSelected;
        this.mPagerIndicatorUnselected = pagerIndicatorUnselected;
        mIndicatorHeight = Math.max(pagerIndicatorSelected.getIntrinsicHeight(), pagerIndicatorUnselected.getIntrinsicHeight());
        mIndicatorItemLength = Math.max(pagerIndicatorSelected.getIntrinsicWidth(), pagerIndicatorUnselected.getIntrinsicWidth());
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = Math.round(mIndicatorHeight + mIndicatorItemMargin);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int itemCount = parent.getAdapter().getItemCount();

        // center horizontally, calculate width and subtract half from center
        float totalLength = mIndicatorItemLength * itemCount;
        float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

        // center vertically in the allotted space
        float indicatorPosY = parent.getHeight() - (mIndicatorHeight + mIndicatorItemMargin) / 2F;

        // draw normal lines
        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);

        // find active page (which should be highlighted)
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int activePosition = layoutManager.findFirstVisibleItemPosition();
        if (activePosition == RecyclerView.NO_POSITION) {
            return;
        }

// find offset of active page (if the user is scrolling)
        final View activeChild = layoutManager.findViewByPosition(activePosition);
        int left = activeChild.getLeft();
        int width = activeChild.getWidth();

        // draw highlighted line
        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, itemCount);
    }

    private void drawInactiveIndicators(Canvas c, float indicatorStartX,
                                        float indicatorPosY, int itemCount) {
        if (mPagerIndicatorUnselected.getConstantState() == null)
            return;

        // width of item indicator including padding
        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

        float start = indicatorStartX;
        for (int i = 0; i < itemCount; i++) {
            // draw the line for every
            Drawable drawable = mPagerIndicatorUnselected.getConstantState().newDrawable();
            drawable.mutate();
            drawable.setBounds(Math.round(start), Math.round(indicatorPosY),
                    Math.round(start + mIndicatorItemLength), Math.round(indicatorPosY + mIndicatorHeight));
            drawable.draw(c);

            start += itemWidth;
        }
    }

    private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                int highlightPosition, int itemCount) {
        // width of item indicator including padding
        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

        float highlightStart = indicatorStartX + itemWidth * highlightPosition;

        mPagerIndicatorSelected.setBounds(Math.round(highlightStart), Math.round(indicatorPosY),
                Math.round(highlightStart + mIndicatorItemLength), Math.round(indicatorPosY + mIndicatorHeight));
        mPagerIndicatorSelected.draw(c);
    }
}
