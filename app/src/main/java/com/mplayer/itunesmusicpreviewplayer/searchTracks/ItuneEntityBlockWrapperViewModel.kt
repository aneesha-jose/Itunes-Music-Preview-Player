package com.mplayer.itunesmusicpreviewplayer.searchTracks

import android.arch.lifecycle.LiveData
import com.mplayer.itunesmusicpreviewplayer.base.BaseViewModel
import com.mplayer.itunesmusicpreviewplayer.data.DataRepository
import com.mplayer.itunesmusicpreviewplayer.data.models.ItuneResponse
import com.mplayer.itunesmusicpreviewplayer.data.models.SearchedWords
import java.util.*
import javax.inject.Inject

class ItuneEntityBlockWrapperViewModel @Inject constructor(dataRepository: DataRepository) : BaseViewModel<ItuneResponse>(dataRepository) {

    private lateinit var searchWordsData: LiveData<List<SearchedWords>>

    fun getAllSearchedWords(): LiveData<List<SearchedWords>> {
        searchWordsData = dataRepository.getAllSearchedWords()
        return searchWordsData
    }

    override fun onSuccess(callTag: String?, response: ItuneResponse?, extras: HashMap<String, Any>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(callTag: String?, e: Throwable?, extras: HashMap<String, Any>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}