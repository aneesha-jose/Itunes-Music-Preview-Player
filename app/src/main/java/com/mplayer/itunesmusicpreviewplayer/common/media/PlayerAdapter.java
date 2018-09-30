package com.mplayer.itunesmusicpreviewplayer.common.media;

/**
 * Allows {@link com.mplayer.itunesmusicpreviewplayer.base.activity.BaseActivity} to control media playback of {@link MediaPlayerHolder}.
 */
public interface PlayerAdapter {

    void loadMedia(String track);

    void release();

    boolean isPlaying();

    void play();

    void reset();

    void pause();

    void initializeProgressCallback();

    void seekTo(int position);
}

