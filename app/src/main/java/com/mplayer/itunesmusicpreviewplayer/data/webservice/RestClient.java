package com.mplayer.itunesmusicpreviewplayer.data.webservice;

import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.BaseUrl;
import com.mplayer.itunesmusicpreviewplayer.android.scopes.ApplicationScope;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by swapnull on 06/12/16.
 */
@ApplicationScope
public class RestClient {

    private final Retrofit retrofit;

    @Inject
    RestClient(OkHttpClient client, GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory rxJavaCallAdapterFactory, @BaseUrl String baseUrl) {
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }

    public ApiService createRetrofitClient() {
        return retrofit.create(ApiService.class);
    }
}
