package com.rcacao.marvelchallenge.view.ui

import android.app.Activity
import android.util.DisplayMetrics
import com.rcacao.marvelchallenge.R
import javax.inject.Inject

class ItemSizeHelper @Inject constructor() {
    operator fun invoke(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels / activity.resources.getInteger(R.integer.item_columns)
    }
}