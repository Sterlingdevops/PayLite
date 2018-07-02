package com.sterlingng.paylite.ui.home

import com.sterlingng.paylite.data.model.Deal
import com.sterlingng.paylite.ui.base.MvpView

interface HomeMvpView : MvpView {
    fun updateDeals(it: ArrayList<Deal>)
}
