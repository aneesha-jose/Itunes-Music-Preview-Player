package com.mplayer.itunesmusicpreviewplayer;

import com.mplayer.itunesmusicpreviewplayer.android.scopes.ActivityScope;
import com.mplayer.itunesmusicpreviewplayer.base.activity.BaseActivityComponent;
import com.mplayer.itunesmusicpreviewplayer.player.TrackPlayerActivity;
import com.mplayer.itunesmusicpreviewplayer.searchTracks.SearchTracksActivity;
import com.mplayer.itunesmusicpreviewplayer.splash.SplashActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = BaseActivityComponent.class)
public interface DependencyInjectorComponent {
    void injectDependencies(SplashActivity activity);

    void injectDependencies(SearchTracksActivity activity);

    void injectDependencies(TrackPlayerActivity activity);
}
