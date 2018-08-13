package com.sterlingng.views

import android.content.Context
import android.support.v7.widget.LinearLayoutManager

class NoScrollingLinearLayoutManager(val context: Context) : LinearLayoutManager(context) {
    var isScrollEnabled = true

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }

    override fun canScrollHorizontally(): Boolean {
        return isScrollEnabled && super.canScrollHorizontally()
    }
}