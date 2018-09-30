package com.mplayer.itunesmusicpreviewplayer.player;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mplayer.itunesmusicpreviewplayer.DependencyInjectorComponent;
import com.mplayer.itunesmusicpreviewplayer.R;
import com.mplayer.itunesmusicpreviewplayer.base.ViewModelFactory;
import com.mplayer.itunesmusicpreviewplayer.base.activity.BaseActivity;
import com.mplayer.itunesmusicpreviewplayer.common.IntentConstants;
import com.mplayer.itunesmusicpreviewplayer.common.media.MediaPlayerHolder;
import com.mplayer.itunesmusicpreviewplayer.common.media.PlaybackInfoListener;
import com.mplayer.itunesmusicpreviewplayer.common.media.PlayerAdapter;
import com.mplayer.itunesmusicpreviewplayer.data.models.ItuneEntity;
import com.mplayer.itunesmusicpreviewplayer.utils.Optional;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class TrackPlayerActivity extends BaseActivity implements PlaybackListener.Callback {

    private static final String TAG = TrackPlayerActivity.class.getSimpleName();

    public static Intent createInstance(Context context, ItuneEntity ituneEntity) {
        Intent intent = new Intent(context, TrackPlayerActivity.class);
        intent.putExtra(IntentConstants.ITUNE_TRACK, ituneEntity);
        //Since it acts as a landing home page
        return intent;
    }

    @Inject
    Picasso picasso;

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.iv_track_image)
    ImageView ivTrackImage;

    @BindView(R.id.seekbar)
    SeekBar seekBar;

    @BindView(R.id.fab_play)
    FloatingActionButton fabPlay;

    @BindView(R.id.tv_current_track_time)
    TextView tvCurrentTrackTime;

    @BindView(R.id.tv_total_track_time)
    TextView tvTotalTrackTime;

    @BindView(R.id.tv_track_name)
    TextView tvTrackName;

    @BindView(R.id.tv_track_info)
    TextView tvTrackInfo;

    @BindView(R.id.ib_fav)
    ImageButton ibFav;

    private ItuneEntity track;
    private boolean isUserSeeking;
    private PlayerAdapter mPlayerAdapter;
    private PlaybackListener playbackListener;

    private TrackPlayerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_player);

        initIntentVariables();
    }

    private void initIntentVariables() {
        track = getIntent().getParcelableExtra(IntentConstants.ITUNE_TRACK);
        if (track == null) {
            trackNotFoundError();
            return;
        }
        initView();
    }

    private void initView() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TrackPlayerViewModel.class);
        initTrackData();
        setUpTrackImage();
        initializePlaybackController();
        initializeSeekbar();
        mPlayerAdapter.loadMedia(track.getPreviewUrl());
    }

    private void initTrackData() {
        tvTrackName.setText(track.getTrackName());
        tvTrackInfo.setText(track.getArtistsName());
        checkFavTrack();
    }

    private void checkFavTrack() {
        ibFav.setSelected(viewModel.isAFavTrack(track));
    }

    private void setUpTrackImage() {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.shape);
        if (drawable != null)
            DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.colorPrimaryDark));
        picasso.load(Optional.orElse(track.getArtworkUrl100(), "").get())
                .placeholder(drawable)
                .error(drawable)
                .into(ivTrackImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "onSuccess");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                });
    }

    private void initializePlaybackController() {
        playbackListener = new PlaybackListener(this);
        MediaPlayerHolder mMediaPlayerHolder = new MediaPlayerHolder(this);
        Log.d(TAG, "initializePlaybackController: created MediaPlayerHolder");
        mMediaPlayerHolder.setPlaybackInfoListener(playbackListener);
        mPlayerAdapter = mMediaPlayerHolder;
        Log.d(TAG, "initializePlaybackController: MediaPlayerHolder progress callback set");
    }


    private void initializeSeekbar() {
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int userSelectedPosition = 0;

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        isUserSeeking = true;
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            userSelectedPosition = progress;
                        }
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        isUserSeeking = false;
                        if (playbackListener.getPlayerState() != PlaybackInfoListener.State.INVALID)
                            mPlayerAdapter.seekTo(userSelectedPosition);
                    }
                });
    }

    private void trackNotFoundError() {
        Toast.makeText(this, getString(R.string.track_not_found), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void callDependencyInjector(DependencyInjectorComponent injectorComponent) {
        injectorComponent.injectDependencies(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (track != null)
            mPlayerAdapter.loadMedia(track.getPreviewUrl());
        Log.d(TAG, "onStart: create MediaPlayer");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isChangingConfigurations() && mPlayerAdapter.isPlaying()) {
            Log.d(TAG, "onStop: don't release MediaPlayer as screen is rotating & playing");
        } else {
            mPlayerAdapter.release();
            Log.d(TAG, "onStop: release MediaPlayer");
        }
    }

    @OnClick(R.id.fab_play)
    void onPlayClicked() {
        if (playbackListener.getPlayerState() == PlaybackInfoListener.State.INVALID)
            return;
        if (mPlayerAdapter.isPlaying()) {
            mPlayerAdapter.pause();
            return;
        }
        mPlayerAdapter.play();
    }

    @OnClick(R.id.ib_playlist)
    void onPlaylistClicked() {
        onBackPressed();
    }

    @OnClick(R.id.ib_fav)
    void onFavClicked() {
        Runnable runnable = () -> {
            dismissLoadingDialog();
            checkFavTrack();
        };
        showLoadingDialog("", false);
        if (ibFav.isSelected())
            viewModel.delTrackTofavs(track, runnable);
        else
            viewModel.addTrackTofavs(track, runnable);

    }

    @Override
    public SeekBar getSeekBar() {
        return seekBar;
    }

    @Override
    public boolean isUserSeeking() {
        return isUserSeeking;
    }

    @Override
    public void onPlayStateChanged(boolean isPlaying) {
        fabPlay.setSelected(isPlaying);
    }

    @Override
    public TextView getCurrentTimeTv() {
        return tvCurrentTrackTime;
    }

    @Override
    public TextView getTotalTimeTv() {
        return tvTotalTrackTime;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_down_bottom, R.anim.stay);
    }
}
