package com.mplayer.itunesmusicpreviewplayer.player

import com.mplayer.itunesmusicpreviewplayer.base.BaseViewModel
import com.mplayer.itunesmusicpreviewplayer.data.DataRepository
import com.mplayer.itunesmusicpreviewplayer.data.models.ItuneEntity
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TrackPlayerViewModel(dataRepository: DataRepository) : BaseViewModel<ItuneEntity>(dataRepository) {

    fun addTrackTofavs(ituneEntity: ItuneEntity, runnable: Runnable) {
        Flowable.fromCallable {
            dataRepository.addFavTrack(ituneEntity)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { runnable.run() }

    }

    fun delTrackTofavs(ituneEntity: ItuneEntity, runnable: Runnable) {
        Flowable.fromCallable {
            dataRepository.deleteFavTrack(ituneEntity)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { runnable.run() }
    }

    fun isAFavTrack(ituneEntity: ItuneEntity): Boolean {
        return dataRepository.isAFavTrack(ituneEntity)
    }


}