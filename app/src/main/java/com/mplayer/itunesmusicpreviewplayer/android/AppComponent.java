package com.mplayer.itunesmusicpreviewplayer.android;

import android.content.Context;

import com.mplayer.itunesmusicpreviewplayer.android.modules.AppModule;
import com.mplayer.itunesmusicpreviewplayer.android.modules.NetworkModule;
import com.mplayer.itunesmusicpreviewplayer.android.modules.PicassoModule;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.ApplicationContext;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.LocalDataSource;
import com.mplayer.itunesmusicpreviewplayer.android.scopes.ApplicationScope;
import com.mplayer.itunesmusicpreviewplayer.data.AppDatabase;
import com.mplayer.itunesmusicpreviewplayer.data.SharedPref;
import com.squareup.picasso.Picasso;

import dagger.Component;

/**
 * Created by Aneesha on 12/10/17.
 */
@ApplicationScope
@Component(modules = {AppModule.class, NetworkModule.class, PicassoModule.class})
public interface AppComponent {

    @ApplicationContext
    Context getContext();

    com.mplayer.itunesmusicpreviewplayer.data.DataRepository getRemoteDataSource();

    @LocalDataSource
    AppDatabase getLocalDataSource();

    Picasso getPicasso();

    SharedPref getSharedPref();

}
