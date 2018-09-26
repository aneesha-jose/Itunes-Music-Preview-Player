package com.mplayer.itunesmusicpreviewplayer.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.mplayer.itunesmusicpreviewplayer.common.Constants
import com.mplayer.itunesmusicpreviewplayer.data.models.SearchedWords


@Dao
interface SearchedWordsDao {

    @Query("SELECT * FROM " + Constants.SEARCHED_WORDS)
    fun getAll(): LiveData<List<SearchedWords>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSearchedWord(searchedWords: SearchedWords)

}