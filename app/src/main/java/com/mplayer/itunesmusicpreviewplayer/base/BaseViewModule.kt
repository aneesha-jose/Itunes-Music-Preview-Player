package com.mplayer.itunesmusicpreviewplayer.base

import android.arch.lifecycle.ViewModel
import com.mplayer.itunesmusicpreviewplayer.data.DataRepository
import com.mplayer.itunesmusicpreviewplayer.data.webservice.ApiCallbacks
import com.mplayer.itunesmusicpreviewplayer.data.webservice.ResponseWrapper
import java.util.*

abstract class BaseViewModel<T>(protected val dataRepository: DataRepository) : ViewModel(), ApiCallbacks<T> {
    override fun onSuccess(callTag: String?, response: ResponseWrapper<T>?, extras: HashMap<String, Any>?) {
    }

    override fun onError(callTag: String?, response: ResponseWrapper<T>?, extras: HashMap<String, Any>?) {}

}


