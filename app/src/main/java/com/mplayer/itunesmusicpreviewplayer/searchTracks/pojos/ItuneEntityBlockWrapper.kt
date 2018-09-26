package com.mplayer.itunesmusicpreviewplayer.searchTracks.pojos

import com.mplayer.itunesmusicpreviewplayer.data.models.ItuneEntity

data class ItuneEntityBlockWrapper(var startIndex: Int? = 0,
                                   var endIndex: Int,
                                   var entities: List<ItuneEntity>? = emptyList())