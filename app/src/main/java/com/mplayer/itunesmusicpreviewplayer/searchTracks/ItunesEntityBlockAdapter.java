package com.mplayer.itunesmusicpreviewplayer.searchTracks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mplayer.itunesmusicpreviewplayer.R;
import com.mplayer.itunesmusicpreviewplayer.common.Constants;
import com.mplayer.itunesmusicpreviewplayer.data.models.ItuneEntity;
import com.mplayer.itunesmusicpreviewplayer.searchTracks.pojos.ItuneEntityBlockWrapper;
import com.mplayer.itunesmusicpreviewplayer.utils.Optional;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;

public class ItunesEntityBlockAdapter extends RecyclerView.Adapter<ItunesEntityBlockAdapter.ViewHolder> {

    List<ItuneEntityBlockWrapper> items;
    private Listener mListener;
    private Picasso picasso;

    public ItunesEntityBlockAdapter(Listener listener, Picasso picasso) {
        this.mListener = listener;
        this.picasso = picasso;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_track_block, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ItuneEntityBlockWrapper item = items.get(position);
        if (item == null)
            return;
        Context context = viewHolder.itemView.getContext();
        viewHolder.llTrackHolder.removeAllViews();
        if (viewHolder.subViews == null || viewHolder.subViews.size() != Optional.orElse(item.getEntities(), new ArrayList<ItuneEntity>()).get().size())
            viewHolder.subViews = getTrackViewsInflated(item, viewHolder.llTrackHolder, context);
        for (int i = 0; i < viewHolder.subViews.size(); i++) {
            if (i < item.getEntities().size()) {
                View view = viewHolder.subViews.get(i);
                ItuneEntity ituneEntity = item.getEntities().get(i);
                handleTrackViewWithViewHolder(view, ituneEntity, context);
                view.setOnClickListener(view1 -> {
                    if (mListener != null)
                        mListener.onItuneTrackClicked(ituneEntity);
                });
                viewHolder.llTrackHolder.addView(view);
            }
        }
    }


    private List<View> getTrackViewsInflated(ItuneEntityBlockWrapper item, LinearLayout parent, Context context) {
        return Flowable.fromIterable(Optional.orElse(item.getEntities(), new ArrayList<ItuneEntity>()).get())
                .map(ituneEntity -> getInflatedItemTrack(parent, context)).toList().blockingGet();
    }

    private View getInflatedItemTrack(LinearLayout parent, Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_track_layout, parent, false);
        return view;
    }

    private void handleTrackViewWithViewHolder(View view, ItuneEntity ituneEntity, Context context) {
        TrackViewHolder viewHolder = new TrackViewHolder(view);
        fillTrackDetails(viewHolder, ituneEntity, context);
    }

    private void fillTrackDetails(TrackViewHolder viewHolder, ItuneEntity ituneEntity, Context context) {
        viewHolder.tvTrackName.setText(Optional.orElse(ituneEntity.getTrackName(), "").get());
        viewHolder.tvTrackInfo.setText(String.format("%s | %s", Optional.orElse(ituneEntity.getArtistsName(), Constants.NA).get(), Optional.orElse(ituneEntity.getCollectionName(), Constants.NA).get()));
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.shape);
        if (drawable != null)
            DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.colorPrimaryDark));
        picasso.load(Optional.orElse(ituneEntity.getArtworkUrl60(), "").get())
                .placeholder(drawable)
                .error(drawable)
                .fit()
                .noFade()
                .into(viewHolder.trackImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<ItuneEntityBlockWrapper> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_tracks_holder)
        LinearLayout llTrackHolder;

        List<View> subViews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TrackViewHolder {
        @BindView(R.id.track_image)
        ImageView trackImage;

        @BindView(R.id.tv_track_name)
        TextView tvTrackName;

        @BindView(R.id.tv_track_info)
        TextView tvTrackInfo;

        public TrackViewHolder(@NonNull View itemView) {
            ButterKnife.bind(this, itemView);
        }

    }

    public interface Listener {

        void onItuneTrackClicked(ItuneEntity track);
    }
}
