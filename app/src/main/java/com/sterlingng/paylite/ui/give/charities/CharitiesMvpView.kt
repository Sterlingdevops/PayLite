package com.sterlingng.paylite.ui.give.charities

import com.sterlingng.paylite.data.model.Charity
import com.sterlingng.paylite.ui.base.MvpView

interface CharitiesMvpView : MvpView {
    fun updateCharities(it: ArrayList<Charity>)
}
