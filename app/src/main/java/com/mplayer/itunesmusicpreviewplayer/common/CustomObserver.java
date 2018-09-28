package com.mplayer.itunesmusicpreviewplayer.common;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.mplayer.itunesmusicpreviewplayer.data.webservice.ResponseWrapper;

public class CustomObserver<T> implements Observer<ResponseWrapper<T>> {
    private ChangeListener<T> changeListener;
    private static final int ERROR_CODE = 0;

    public CustomObserver(ChangeListener<T> changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public void onChanged(@Nullable ResponseWrapper<T> tDataWrapper) {
        if (tDataWrapper != null) {
            if (tDataWrapper.getApiException() != null) {
                changeListener.onException(tDataWrapper.getApiException());
            } else {
                changeListener.onSuccess(tDataWrapper.getData());
            }
            return;
        }
    }

    public interface ChangeListener<T> {
        void onSuccess(T dataWrapper);

        void onException(Throwable exception);
    }
}