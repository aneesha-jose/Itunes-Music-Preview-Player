package com.mplayer.itunesmusicpreviewplayer.data.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.mplayer.itunesmusicpreviewplayer.common.Constants

@Entity(tableName = Constants.SEARCHED_WORDS)
data class SearchedWords(@PrimaryKey(autoGenerate = true) var uid: Long = 0,
                         @ColumnInfo(name = Constants.SEARCH_TEXT) var searchtext: String) {

    override fun toString(): String {
        return searchtext
    }
}