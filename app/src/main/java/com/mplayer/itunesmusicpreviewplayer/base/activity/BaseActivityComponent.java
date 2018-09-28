package com.mplayer.itunesmusicpreviewplayer.base.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.mplayer.itunesmusicpreviewplayer.android.AppComponent;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.ActivityContext;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.ApplicationContext;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.LoadingDialog;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.LocalDataSource;
import com.mplayer.itunesmusicpreviewplayer.android.scopes.BaseActivityScope;
import com.mplayer.itunesmusicpreviewplayer.data.AppDatabase;
import com.mplayer.itunesmusicpreviewplayer.data.DataRepository;
import com.mplayer.itunesmusicpreviewplayer.data.SharedPref;
import com.squareup.picasso.Picasso;

import dagger.Component;

/**
 * Created by Aneesha on 13/10/17.
 */
@BaseActivityScope
@Component(modules = BaseActivityModule.class, dependencies = AppComponent.class)
public interface BaseActivityComponent {

    @ApplicationContext
    Context getContext();

    @ActivityContext
    Context getActivityContext();

    Activity getActivity();

    DataRepository getRemoteDataSource();

    @LocalDataSource
    AppDatabase getLocalDataSource();

    Picasso getPicasso();

    @LoadingDialog
    Dialog loadindDialog();

    SharedPref getSharedPref();

}
