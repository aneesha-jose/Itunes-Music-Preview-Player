package com.mplayer.itunesmusicpreviewplayer.searchTracks

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mplayer.itunesmusicpreviewplayer.base.BaseViewModel
import com.mplayer.itunesmusicpreviewplayer.common.ApiParameters
import com.mplayer.itunesmusicpreviewplayer.common.Constants
import com.mplayer.itunesmusicpreviewplayer.data.DataRepository
import com.mplayer.itunesmusicpreviewplayer.data.models.ItuneResponse
import com.mplayer.itunesmusicpreviewplayer.data.models.SearchedWords
import com.mplayer.itunesmusicpreviewplayer.data.webservice.ApiCallTags
import com.mplayer.itunesmusicpreviewplayer.data.webservice.ResponseWrapper
import com.mplayer.itunesmusicpreviewplayer.searchTracks.pojos.ItuneEntityBlockWrapper
import com.mplayer.itunesmusicpreviewplayer.utils.makeNetworkCall
import java.util.*
import kotlin.collections.LinkedHashMap

class ItuneEntityBlockWrapperViewModel constructor(dataRepository: DataRepository) : BaseViewModel<ItuneResponse>(dataRepository) {

    var limit: Int = 4
    private lateinit var searchWordsData: LiveData<List<SearchedWords>>
    private var tracksData: MutableLiveData<ResponseWrapper<List<ItuneEntityBlockWrapper>>> = MutableLiveData()
    private var totalResultCount: MutableLiveData<String> = MutableLiveData()

    fun getAllSearchedWords(): LiveData<List<SearchedWords>> {
        searchWordsData = dataRepository.getAllSearchedWords()
        return searchWordsData
    }

    fun searchTracks(constraint: String, limit: Int = this.limit) {
        this.limit = limit
        var options = LinkedHashMap<String, String>()
        options.put(ApiParameters.TERM, constraint)
        options.put(ApiParameters.LIMIT, limit.toString())
        makeNetworkCall(ApiCallTags.GET_TRACKS, dataRepository.getTracksToSearch(options), this, null)
    }

    override fun onSuccess(callTag: String?, response: ResponseWrapper<ItuneResponse>?, extras: HashMap<String, Any>?) {
        if (ApiCallTags.GET_TRACKS == callTag) {
            handleGetTrackSuccessResponse(response)
        }
    }

    private fun handleGetTrackSuccessResponse(response: ResponseWrapper<ItuneResponse>?) {
        val ituneResponse = response?.data
        val trackblocks = ituneResponse?.results?.filter { ituneEntity -> Constants.TRACK == ituneEntity.wrapperType }?.chunked(limit)
        totalResultCount.value = ituneResponse?.resultCount.toString()
        tracksData.value = ResponseWrapper(data = trackblocks?.map { list -> ItuneEntityBlockWrapper(list) })
    }

    override fun onError(callTag: String?, response: ResponseWrapper<ItuneResponse>?, extras: HashMap<String, Any>?) {
        if (ApiCallTags.GET_TRACKS.equals(callTag)) {
            handleGetTrackErrorResponse(response)
        }
    }

    private fun handleGetTrackErrorResponse(response: ResponseWrapper<ItuneResponse>?) {
        totalResultCount.value = "-"
        tracksData.value = ResponseWrapper(apiException = response?.apiException)
    }

}