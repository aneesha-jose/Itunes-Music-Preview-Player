package com.mplayer.itunesmusicpreviewplayer.splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.mplayer.itunesmusicpreviewplayer.DependencyInjectorComponent;
import com.mplayer.itunesmusicpreviewplayer.R;
import com.mplayer.itunesmusicpreviewplayer.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnEditorAction;

public class SplashActivity extends BaseActivity {

    private static final long STAY_IN_PLACE_TIME = 2 * 1000; // 2 secs

    @BindView(R.id.et_search)
    EditText etSearch;

    private long timeoutMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        timeoutMillis = System.currentTimeMillis() + STAY_IN_PLACE_TIME;

        calculateScreenSizeItemCount();
    }

    //TODO implement size calculator
    private void calculateScreenSizeItemCount() {
        View view = getLayoutInflater().inflate(R.layout.item_track_layout, null);
        /*view.getH()
        ActivityUtilsKt.*/
        handleSearchVisibility();
    }

    @OnEditorAction(value = R.id.et_search,)
    void onSearch(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
            
        }
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
