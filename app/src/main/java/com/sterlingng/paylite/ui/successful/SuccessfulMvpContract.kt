package com.sterlingng.paylite.ui.successful

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface SuccessfulMvpContract<V : SuccessfulMvpView> : MvpPresenter<V> {
    fun loadCategories()
}
