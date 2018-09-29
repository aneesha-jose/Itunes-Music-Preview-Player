package com.mplayer.itunesmusicpreviewplayer.player;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;
import android.widget.Toast;

import com.mplayer.itunesmusicpreviewplayer.DependencyInjectorComponent;
import com.mplayer.itunesmusicpreviewplayer.R;
import com.mplayer.itunesmusicpreviewplayer.base.activity.BaseActivity;
import com.mplayer.itunesmusicpreviewplayer.common.IntentConstants;
import com.mplayer.itunesmusicpreviewplayer.data.models.ItuneEntity;
import com.mplayer.itunesmusicpreviewplayer.utils.Optional;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;

public class TrackPlayerActivity extends BaseActivity {

    public static Intent createInstance(Context context, ItuneEntity ituneEntity) {
        Intent intent = new Intent(context, TrackPlayerActivity.class);
        intent.putExtra(IntentConstants.ITUNE_TRACK, ituneEntity);
        //Since it acts as a landing home page
        return intent;
    }

    @Inject
    Picasso picasso;

    @BindView(R.id.iv_track_image)
    ImageView ivTrackImage;

    private ItuneEntity track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_player);

        initIntentVariables();
    }

    private void initView() {
        setUpTrackImage();
    }

    private void setUpTrackImage() {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.shape);
        if (drawable != null)
            DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.colorPrimaryDark));
        picasso.load(Optional.orElse(track.getArtworkUrl100(), "").get())
                .placeholder(drawable)
                .error(drawable)
                .fit()
                .noFade()
                .into(ivTrackImage);
    }

    private void initIntentVariables() {
        track = getIntent().getParcelableExtra(IntentConstants.ITUNE_TRACK);
        if (track == null) {
            trackNotFoundError();
            return;
        }
        initView();
    }

    private void trackNotFoundError() {
        Toast.makeText(this, getString(R.string.track_not_found), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void callDependencyInjector(DependencyInjectorComponent injectorComponent) {
        injectorComponent.injectDependencies(this);
    }
}
