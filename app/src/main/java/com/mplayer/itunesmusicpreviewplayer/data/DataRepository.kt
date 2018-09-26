package com.mplayer.itunesmusicpreviewplayer.data

import android.arch.lifecycle.LiveData
import com.mplayer.itunesmusicpreviewplayer.data.models.ItuneEntity
import com.mplayer.itunesmusicpreviewplayer.data.models.ItuneResponse
import com.mplayer.itunesmusicpreviewplayer.data.models.SearchedWords
import com.mplayer.itunesmusicpreviewplayer.data.webservice.ApiService
import io.reactivex.Flowable

class DataRepository(val apiService: ApiService, val appDatabase: AppDatabase) : ApiService {
    override fun getTracksToSearch(options: MutableMap<String, String>?): Flowable<ItuneResponse> {
        return apiService.getTracksToSearch(options)
    }

    fun getAllSearchedWords(): LiveData<List<SearchedWords>> {
        return appDatabase.searchedWordsDao().getAll()
    }

    fun addSearchedWord(searchedWord: String) {
        appDatabase.searchedWordsDao().addSearchedWord(SearchedWords(searchtext = searchedWord))
    }

    fun getAllFavTracks(): LiveData<List<ItuneEntity>> {
        return appDatabase.favTrackInfoDao().getAll()
    }

    fun addFavTrack(favTrack: ItuneEntity) {
        appDatabase.favTrackInfoDao().addFavTrack(favTrack)
    }

    fun deleteFavTrack(favTrack: ItuneEntity) {
        appDatabase.favTrackInfoDao().deleteFavTracks(favTrack)
    }
}