package com.mplayer.itunesmusicpreviewplayer.data.webservice;

import java.util.HashMap;

/**
 * Created by Aneesha on 13/10/17.
 */

public interface ApiCallbacks<T> {

    void onSuccess(@ApiCallTags.ApiCallIdentifiers String callTag, ResponseWrapper<T> response, HashMap<String, Object> extras);

    void onError(@ApiCallTags.ApiCallIdentifiers String callTag, ResponseWrapper<T> response, HashMap<String, Object> extras);

}
