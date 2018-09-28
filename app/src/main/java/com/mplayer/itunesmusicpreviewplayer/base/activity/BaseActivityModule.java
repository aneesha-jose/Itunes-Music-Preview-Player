package com.mplayer.itunesmusicpreviewplayer.base.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.mplayer.itunesmusicpreviewplayer.R;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.ActivityContext;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.LoadingDialog;
import com.mplayer.itunesmusicpreviewplayer.android.scopes.BaseActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aneesha on 13/10/17.
 */
@Module
public class BaseActivityModule {

    private final Activity activity;

    BaseActivityModule(Activity activity) {
        this.activity = activity;
    }

    @BaseActivityScope
    @Provides
    public Activity activity() {
        return activity;
    }

    @ActivityContext
    @Provides
    Context activityContext() {
        return activity;
    }

    @LoadingDialog
    @Provides
    public Dialog loadingDialog(Activity context) {
        Dialog loadingDialog = new Dialog(context, R.style.DialogFullscreen);
        loadingDialog.setContentView(R.layout.loading_progress_blocker);
        loadingDialog.setCanceledOnTouchOutside(false);
        return loadingDialog;
    }
}
