package com.mplayer.itunesmusicpreviewplayer.data.webservice;

import com.mplayer.itunesmusicpreviewplayer.data.models.ItuneResponse;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("search?")
    Flowable<ItuneResponse> getTracksToSearch(@QueryMap Map<String, String> options); //done
}
