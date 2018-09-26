package com.mplayer.itunesmusicpreviewplayer.data.webservice;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface ApiCallTags {

    String GET_TRACKS = "GetTracks";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({GET_TRACKS})
    @interface ApiCallIdentifiers {
    }
}
