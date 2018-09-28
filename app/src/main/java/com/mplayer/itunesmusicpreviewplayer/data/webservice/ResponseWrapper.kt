package com.mplayer.itunesmusicpreviewplayer.data.webservice

data class ResponseWrapper<T>(val apiException: Throwable? = null,
                              val data: T? = null)
