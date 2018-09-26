package com.mplayer.itunesmusicpreviewplayer.android;

import android.app.Application;

import com.mplayer.itunesmusicpreviewplayer.android.modules.AppModule;

public class MPlayerApp extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }

    public AppComponent getComponent() {
        return component;
    }
}
