package com.mplayer.itunesmusicpreviewplayer.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.mplayer.itunesmusicpreviewplayer.common.Constants
import com.mplayer.itunesmusicpreviewplayer.data.models.ItuneEntity


@Dao
interface FavTrackInfoDao {

    @Query("SELECT * FROM " + Constants.FAV_TRACK_INFO)
    fun getAll(): LiveData<List<ItuneEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavTrack(favTrack: ItuneEntity)

    @Delete
    fun deleteFavTracks(vararg favTracks: ItuneEntity)

    @Query("SELECT * FROM " + Constants.FAV_TRACK_INFO + " where " + Constants.TRACK_ID + " = :favTrackId")
    fun getFavTrack(favTrackId: String): ItuneEntity?
}