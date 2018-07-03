package com.sterlingng.paylite.ui.give.categories

import com.sterlingng.paylite.data.model.Category
import com.sterlingng.paylite.ui.base.MvpView

interface CategoriesMvpView : MvpView {
    fun updateDeals(it: ArrayList<Category>)
}
