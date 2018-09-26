package com.mplayer.itunesmusicpreviewplayer.utils

import android.util.Log
import com.mplayer.itunesmusicpreviewplayer.data.webservice.ApiCallbacks
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import java.util.*

fun <T> makeNetworkCall(callTag: String, flowable: Flowable<T>, apiCallbacks: ApiCallbacks<T>, extras: HashMap<String, Any>): Disposable {
    return flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread(), true)
            .subscribeWith(object : DisposableSubscriber<T>() {
                override fun onNext(response: T) {
                    apiCallbacks.onSuccess(callTag, response, extras)
                }

                override fun onError(e: Throwable) {
                    Log.e(callTag, e.message, e)
                    apiCallbacks.onError(callTag, e, extras)
                }

                override fun onComplete() {
                }
            }) as Disposable
}