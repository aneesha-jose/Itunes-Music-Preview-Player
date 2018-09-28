package com.mplayer.itunesmusicpreviewplayer.base.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.mplayer.itunesmusicpreviewplayer.DaggerDependencyInjectorComponent;
import com.mplayer.itunesmusicpreviewplayer.DependencyInjectorComponent;
import com.mplayer.itunesmusicpreviewplayer.android.MPlayerApp;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.ActivityContext;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.ApplicationContext;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.LoadingDialog;
import com.mplayer.itunesmusicpreviewplayer.data.SharedPref;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @LoadingDialog
    @Inject
    public Dialog loadingDialog;

    @ActivityContext
    @Inject
    protected Context context;

    @ApplicationContext
    @Inject
    public Context appContext;

    @Inject
    public SharedPref sharedPref;

    BaseActivityComponent activityComponent;

    @SuppressLint("InflateParams")
    @Override

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        initialiseBaseActivityComponent();
        initialiseDaggerDependencies();
    }

    private void initialiseBaseActivityComponent() {
        activityComponent = DaggerBaseActivityComponent.builder()
                .appComponent(((MPlayerApp) getApplicationContext()).getComponent())
                .baseActivityModule(new BaseActivityModule(this))
                .build();
    }

    protected void initialiseDaggerDependencies() {
        callDependencyInjector(initialiseDaggerInjector());
    }

    private DependencyInjectorComponent initialiseDaggerInjector() {
        return DaggerDependencyInjectorComponent.builder()
                .baseActivityComponent(activityComponent).build();
    }

    protected abstract void callDependencyInjector(DependencyInjectorComponent injectorComponent);

    public BaseActivityComponent getBaseActivityComponent() {
        return activityComponent;
    }
}
