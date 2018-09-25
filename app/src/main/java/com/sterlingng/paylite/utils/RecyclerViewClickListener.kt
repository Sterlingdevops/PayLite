package com.sterlingng.paylite.utils

import android.view.View

interface RecyclerViewClickListener {
    fun recyclerViewItemClicked(v: View, position: Int)
}

interface RecyclerViewLongClickListener {
    fun recyclerViewItemLongClicked(v: View, position: Int)
}