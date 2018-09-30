package com.mplayer.itunesmusicpreviewplayer.player;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mplayer.itunesmusicpreviewplayer.common.media.PlaybackInfoListener;

public class PlaybackListener extends PlaybackInfoListener {

    private Callback callback;
    private int playerState = State.INVALID;

    public PlaybackListener(Callback callback) {
        this.callback = callback;
    }


    private static final String TAG = PlaybackListener.class.getSimpleName();

    @Override
    public void onDurationChanged(int duration) {
        callback.getSeekBar().setMax(duration);
        callback.getTotalTimeTv().setText(DateUtils.formatDateTime(callback.getContext(), duration, DateUtils.FORMAT_ABBREV_TIME));
        Log.d(TAG, String.format("setPlaybackDuration: setMax(%d)", duration));
    }

    @Override
    public void onPositionChanged(int position) {
        if (!callback.isUserSeeking()) {
            callback.getSeekBar().setProgress(position);
            Log.d(TAG, String.format("setPlaybackPosition: setProgress(%d)", position));
        }
        callback.getCurrentTimeTv().setText(DateUtils.formatDateTime(callback.getContext(), position, DateUtils.FORMAT_ABBREV_TIME));
    }

    @Override
    public void onStateChanged(@State int state) {
        playerState = state;
        callback.onPlayStateChanged(playerState == State.PLAYING);
        String stateToString = PlaybackInfoListener.convertStateToString(state);
        Log.d(TAG, String.format("onStateChanged(%s)", stateToString));
    }

    @Override
    public void onPlaybackCompleted() {
    }

    public int getPlayerState() {
        return playerState;
    }

    public interface Callback {

        SeekBar getSeekBar();

        boolean isUserSeeking();

        void onPlayStateChanged(boolean isPlaying);

        TextView getCurrentTimeTv();

        TextView getTotalTimeTv();

        Context getContext();

    }
}
