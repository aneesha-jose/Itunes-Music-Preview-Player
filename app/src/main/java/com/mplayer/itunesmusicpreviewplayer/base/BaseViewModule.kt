package com.mplayer.itunesmusicpreviewplayer.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import com.mplayer.itunesmusicpreviewplayer.data.DataRepository
import com.mplayer.itunesmusicpreviewplayer.data.webservice.ApiCallbacks
import javax.inject.Inject

abstract class BaseViewModel<T>(protected val dataRepository: DataRepository) : ViewModel(), ApiCallbacks<T>


