package com.sterlingng.paylite.ui.give.categories

import com.sterlingng.paylite.ui.base.MvpPresenter

interface CategoriesMvpContract<V : CategoriesMvpView> : MvpPresenter<V>
{
    fun loadCategories()
}