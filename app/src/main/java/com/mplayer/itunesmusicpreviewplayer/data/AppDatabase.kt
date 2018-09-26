package com.mplayer.itunesmusicpreviewplayer.data

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.mplayer.itunesmusicpreviewplayer.data.dao.FavTrackInfoDao
import com.mplayer.itunesmusicpreviewplayer.data.dao.SearchedWordsDao
import com.mplayer.itunesmusicpreviewplayer.data.models.ItuneEntity
import com.mplayer.itunesmusicpreviewplayer.data.models.SearchedWords


@Database(entities = [SearchedWords::class, ItuneEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchedWordsDao(): SearchedWordsDao

    abstract fun favTrackInfoDao(): FavTrackInfoDao
}