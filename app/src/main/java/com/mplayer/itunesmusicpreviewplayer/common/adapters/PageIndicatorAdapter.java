package com.mplayer.itunesmusicpreviewplayer.common.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mplayer.itunesmusicpreviewplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageIndicatorAdapter extends RecyclerView.Adapter<PageIndicatorAdapter.ViewHolder> {

    private final int arraySize;
    private int positionUpdate;

    public PageIndicatorAdapter(int arraySize, int positionUpdate) {
        this.arraySize = arraySize;
        this.positionUpdate = positionUpdate;
    }

    @NonNull
    @Override
    public PageIndicatorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_indicator, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageIndicatorAdapter.ViewHolder holder, final int position) {
        holder.ivIndicator.setSelected(position == positionUpdate);
    }

    @Override
    public int getItemCount() {
        return arraySize;
    }

    public void setCurrentPosition(int positionUpdate) {
        int prevPosition = this.positionUpdate;
        this.positionUpdate = positionUpdate;
        if (prevPosition >= 0 && positionUpdate < arraySize)
            notifyItemChanged(prevPosition);
        notifyItemChanged(positionUpdate);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivIndicator)
        ImageView ivIndicator;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}