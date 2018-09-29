package com.mplayer.itunesmusicpreviewplayer.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mplayer.itunesmusicpreviewplayer.DependencyInjectorComponent;
import com.mplayer.itunesmusicpreviewplayer.R;
import com.mplayer.itunesmusicpreviewplayer.base.activity.BaseActivity;
import com.mplayer.itunesmusicpreviewplayer.searchTracks.SearchTracksActivity;
import com.mplayer.itunesmusicpreviewplayer.utils.ActivityUtilsKt;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    private static final long STAY_IN_PLACE_TIME = 2 * 1000; // 2 secs

    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;

    @BindView(R.id.et_search)
    TextView etSearch;

    @BindView(R.id.list_item)
    CardView mView;

    private long timeoutMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        timeoutMillis = System.currentTimeMillis() + STAY_IN_PLACE_TIME;

        calculateScreenSizeItemCount();
    }

    private void calculateScreenSizeItemCount() {
        mView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        // gets called after layout has been done but before display
                        // so we can get the height then hide the view

                        ActivityUtilsKt.calculateScreenSizeItemCountFit(mView.getHeight(), rlRoot.getHeight(), sharedPref);

                        mView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        mView.setVisibility(View.GONE);
                    }

                });
        handleSearchVisibility();
    }

    @OnClick(R.id.et_search)
    void onSearch() {
        startActivity(SearchTracksActivity.createInstance(this));
        overridePendingTransition(R.anim.enter_from_right, R.anim.stay);
        finish();
    }

    private void handleSearchVisibility() {
        if (System.currentTimeMillis() - timeoutMillis < 0) {
            new Handler().postDelayed(() -> etSearch.setVisibility(View.VISIBLE), timeoutMillis - System.currentTimeMillis());
            return;
        }
        etSearch.setVisibility(View.VISIBLE);
    }

    @Override
    protected void callDependencyInjector(DependencyInjectorComponent injectorComponent) {
        injectorComponent.injectDependencies(this);
    }
}
