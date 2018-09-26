package com.mplayer.itunesmusicpreviewplayer.data.webservice;

import android.support.annotation.Nullable;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

/**
 * Created by Aneesha on 13/10/17.
 */

public interface ApiCallbacks<T> {

    void onSuccess(@ApiCallTags.ApiCallIdentifiers String callTag, T response, HashMap<String, Object> extras);

    void onError(@ApiCallTags.ApiCallIdentifiers String callTag, Throwable e, HashMap<String, Object> extras);

}
