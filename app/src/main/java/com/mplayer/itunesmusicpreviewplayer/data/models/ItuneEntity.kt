package com.mplayer.itunesmusicpreviewplayer.data.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mplayer.itunesmusicpreviewplayer.common.ApiParameters
import com.mplayer.itunesmusicpreviewplayer.common.Constants

@Entity(tableName = Constants.FAV_TRACK_INFO)
data class ItuneEntity(@SerializedName(ApiParameters.WRAPPER_TYPE) @ColumnInfo(name = Constants.WRAPPER_TYPE) val wrapperType: String?,
                       @SerializedName(ApiParameters.TRACK_ID) @ColumnInfo(name = Constants.TRACK_ID) @PrimaryKey val trackId: String,
                       @SerializedName(ApiParameters.ARTISTS_NAME) @ColumnInfo(name = Constants.ARTISTS_NAME) val artistsName: String?,
                       @SerializedName(ApiParameters.COLLECTION_NAME) @ColumnInfo(name = Constants.COLLECTION_NAME) val collectionName: String?,
                       @SerializedName(ApiParameters.TRACK_NAME) @ColumnInfo(name = Constants.TRACK_NAME) val trackName: String?,
                       @SerializedName(ApiParameters.PREVIEW_URL) @ColumnInfo(name = Constants.PREVIEW_URL) val previewUrl: String?,
                       @SerializedName(ApiParameters.ARTWORK_URL_30) @ColumnInfo(name = Constants.ARTWORK_URL_30) val artworkUrl30: String?,
                       @SerializedName(ApiParameters.ARTWORK_URL_60) @ColumnInfo(name = Constants.ARTWORK_URL_60) val artworkUrl60: String?,
                       @SerializedName(ApiParameters.ARTWORK_URL_100) @ColumnInfo(name = Constants.ARTWORK_URL_100) val artworkUrl100: String?,
                       @SerializedName(ApiParameters.TRACK_TIME_IN_MILLIS) @ColumnInfo(name = Constants.TRACK_TIME_IN_MILLIS) val trackTimeMillis: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(wrapperType)
        parcel.writeString(trackId)
        parcel.writeString(artistsName)
        parcel.writeString(collectionName)
        parcel.writeString(trackName)
        parcel.writeString(previewUrl)
        parcel.writeString(artworkUrl30)
        parcel.writeString(artworkUrl60)
        parcel.writeString(artworkUrl100)
        parcel.writeString(trackTimeMillis)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItuneEntity> {
        override fun createFromParcel(parcel: Parcel): ItuneEntity {
            return ItuneEntity(parcel)
        }

        override fun newArray(size: Int): Array<ItuneEntity?> {
            return arrayOfNulls(size)
        }
    }
}