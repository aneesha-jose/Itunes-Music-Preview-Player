package com.mplayer.itunesmusicpreviewplayer.android.modules;

import android.app.Application;
import android.content.Context;

import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.ApplicationContext;
import com.mplayer.itunesmusicpreviewplayer.android.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aneesha on 12/10/17.
 */
@Module
public class AppModule {

    private final Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @ApplicationContext
    @ApplicationScope
    @Provides
    public Context getContext() {
        return this.app;
    }

}

