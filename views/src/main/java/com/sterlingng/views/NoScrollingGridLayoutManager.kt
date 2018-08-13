package com.sterlingng.views

import android.content.Context
import android.support.v7.widget.GridLayoutManager

class NoScrollingGridLayoutManager(val context: Context, spanCount: Int) : GridLayoutManager(context, spanCount) {
    var isScrollEnabled = true

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }

    override fun canScrollHorizontally(): Boolean {
        return isScrollEnabled && super.canScrollHorizontally()
    }
}