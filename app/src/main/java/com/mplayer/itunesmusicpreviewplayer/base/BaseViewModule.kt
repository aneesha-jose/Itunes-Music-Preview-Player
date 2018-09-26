package com.mplayer.itunesmusicpreviewplayer.base

import android.arch.lifecycle.ViewModel
import com.mplayer.itunesmusicpreviewplayer.data.webservice.ApiCallbacks

abstract class BaseViewModel<T>(@DataRepository protected val dataRepository: DataRepository) : ViewModel(), ApiCallbacks<T>