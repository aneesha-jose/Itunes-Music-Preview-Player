package com.mplayer.itunesmusicpreviewplayer.android.modules;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.mplayer.itunesmusicpreviewplayer.android.qualifiers.ApplicationContext;
import com.mplayer.itunesmusicpreviewplayer.android.scopes.ApplicationScope;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by Aneesha on 12/10/17.
 */

@Module
public class PicassoModule {

    @ApplicationScope
    @Provides
    OkHttp3Downloader okHttpDownloader(OkHttpClient okHttpClient) {
        return new OkHttp3Downloader(okHttpClient);
    }


    @ApplicationScope
    @Provides
    public Picasso picasso(OkHttp3Downloader okHttpDownloader, @ApplicationContext Context context) {
        Picasso.Builder builder = new Picasso.Builder(context);
        return builder.downloader(okHttpDownloader).loggingEnabled(true).build();
    }

}