package com.mplayer.itunesmusicpreviewplayer.searchTracks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mplayer.itunesmusicpreviewplayer.DependencyInjectorComponent;
import com.mplayer.itunesmusicpreviewplayer.R;
import com.mplayer.itunesmusicpreviewplayer.base.activity.BaseActivity;
import com.mplayer.itunesmusicpreviewplayer.common.IntentConstants;
import com.mplayer.itunesmusicpreviewplayer.common.adapters.PageIndicatorAdapter;

import butterknife.BindView;

public class SearchTracksActivity extends BaseActivity {

    public static Intent createInstance(Context context, String searchterm) {
        Intent intent = new Intent(context, SearchTracksActivity.class);
        intent.putExtra(IntentConstants.SEARCH_TERM, searchterm);
        //Since it acts as a landing home page
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @BindView(R.id.rv_track_blocks)
    RecyclerView rvTrackBlocks;

    @BindView(R.id.rv_page_indicator)
    RecyclerView rvPageIndicator;

    private PageIndicatorAdapter pageIndicatorAdapter;
    private String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tracks);

        initIntentVariables();
        initView();
    }

    private void initIntentVariables() {
        searchTerm = getIntent().getStringExtra(IntentConstants.SEARCH_TERM);
    }

    private void initView() {
        setUpIntentBlockRecyclerView();
    }

    private void setUpIntentBlockRecyclerView() {

    }

    @Override
    protected void callDependencyInjector(DependencyInjectorComponent injectorComponent) {
        injectorComponent.injectDependencies(this);
    }

    private void initializePageIndicator(int arraySize) {
        pageIndicatorAdapter = new PageIndicatorAdapter(arraySize, 0);
        if (arraySize == 0 || arraySize == 1) {
            rvPageIndicator.setVisibility(View.GONE);
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvPageIndicator.setHasFixedSize(false);
        rvPageIndicator.setLayoutManager(linearLayoutManager);
        rvPageIndicator.setAdapter(pageIndicatorAdapter);
        rvPageIndicator.setItemAnimator(new DefaultItemAnimator());
    }
}
