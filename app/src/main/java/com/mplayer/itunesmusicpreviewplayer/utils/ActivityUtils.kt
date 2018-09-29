package com.mplayer.itunesmusicpreviewplayer.utils

import com.mplayer.itunesmusicpreviewplayer.data.SharedPref

fun calculateScreenSizeItemCountFit(inflatedItemHeight: Int, screenHeight: Int, sharedPref: SharedPref) {
    //estimating that the recycler view spans across 3/4th of the screen
    val availableHeight = screenHeight * 3 / 4
    sharedPref.putScreenSizeCount(availableHeight / inflatedItemHeight)
}