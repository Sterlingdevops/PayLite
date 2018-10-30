package com.sterlingng.views

import android.content.Context
import android.support.v7.widget.LinearLayoutManager

open class NoScrollingLinearLayoutManager(open val context: Context) : LinearLayoutManager(context) {
    var isScrollEnabled = false

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }

    override fun canScrollHorizontally(): Boolean {
        return isScrollEnabled && super.canScrollHorizontally()
    }
}