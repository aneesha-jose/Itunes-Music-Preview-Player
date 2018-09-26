package com.mplayer.itunesmusicpreviewplayer.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mplayer.itunesmusicpreviewplayer.common.ApiParameters

data class ItuneResponse(@SerializedName(ApiParameters.RESULT_COUNT) val resultCount: Long?,
                         @SerializedName(ApiParameters.RESULTS) val results: List<ItuneEntity>?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.createTypedArrayList(ItuneEntity))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(resultCount)
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItuneResponse> {
        override fun createFromParcel(parcel: Parcel): ItuneResponse {
            return ItuneResponse(parcel)
        }

        override fun newArray(size: Int): Array<ItuneResponse?> {
            return arrayOfNulls(size)
        }
    }

}