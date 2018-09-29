package com.mplayer.itunesmusicpreviewplayer.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import com.mplayer.itunesmusicpreviewplayer.data.DataRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: DataRepository) : ViewModelProvider.Factory {

    companion object {
        const val TAG: String = "ViewModelFactory"
        const val VIEW_MODEL_ERROR = "ViewModel Not Found"
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (BaseViewModel::class.java.isAssignableFrom(modelClass)) {
            try {
                return modelClass.getConstructor(DataRepository::class.java).newInstance(repository) as T
            } catch (e: RuntimeException) {
                Log.e(TAG, e.message, e)
            }
        }
        if (ViewModel::class.java.isAssignableFrom(modelClass))
            return modelClass.newInstance() as T
        else
            throw IllegalArgumentException(VIEW_MODEL_ERROR)
    }

}