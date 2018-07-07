package com.sterlingng.paylite.ui.give.projects

import com.sterlingng.paylite.data.model.Category
import com.sterlingng.paylite.ui.base.MvpView

interface ProjectsMvpView : MvpView {
    fun updateDeals(it: ArrayList<Category>)
}
