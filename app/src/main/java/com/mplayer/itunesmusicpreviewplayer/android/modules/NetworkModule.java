package com.mplayer.itunesmusicpreviewplayer.android.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.ApplicationContext;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.BaseUrl;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.LocalDataSource;
import com.mplayer.itunesmusicpreviewplayer.android.scopes.ApplicationScope;
import com.mplayer.itunesmusicpreviewplayer.common.Constants;
import com.mplayer.itunesmusicpreviewplayer.data.AppDatabase;
import com.mplayer.itunesmusicpreviewplayer.data.webservice.ApiService;
import com.mplayer.itunesmusicpreviewplayer.data.webservice.RestClient;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aneesha on 12/10/17.
 */

@Module
public class NetworkModule {

    @ApplicationScope
    @Provides
    OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
    }

    @ApplicationScope
    @Provides
    HttpLoggingInterceptor httpLoggingInterceptor(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(level);
        return interceptor;
    }

    // TODO : GO LIVE STEP : SET LOG LEVEL TO NONE
    @ApplicationScope
    @Provides
    HttpLoggingInterceptor.Level logLevel() {
        return HttpLoggingInterceptor.Level.BODY;
    }

    @ApplicationScope
    @Provides
    GsonConverterFactory gsonConverterFactory() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return GsonConverterFactory.create(gsonBuilder.create());
    }

    @ApplicationScope
    @Provides
    RxJava2CallAdapterFactory rxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @ApplicationScope
    @Provides
    ApiService getApiService(RestClient restClient) {
        return restClient.createRetrofitClient();
    }

    @BaseUrl
    @ApplicationScope
    @Provides
    String getBaseUrl() {
        return "http://itunes.apple.com/";
    }

    @ApplicationScope
    @Provides
    public Gson gson() {
        return new Gson();
    }

    @LocalDataSource
    @ApplicationScope
    @Provides
    public AppDatabase getLocalDataSource(@ApplicationContext Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, Constants.DB_NAME).build();
    }

    @ApplicationScope
    @Provides
    public com.mplayer.itunesmusicpreviewplayer.data.DataRepository getDataRepository(ApiService apiService, @LocalDataSource AppDatabase localDataSource) {
        return new com.mplayer.itunesmusicpreviewplayer.data.DataRepository(apiService, localDataSource);
    }

}
