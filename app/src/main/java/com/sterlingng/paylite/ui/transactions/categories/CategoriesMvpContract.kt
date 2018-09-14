package com.sterlingng.paylite.ui.transactions.categories

import com.sterlingng.paylite.ui.base.MvpPresenter

interface CategoriesMvpContract<V : CategoriesMvpView> : MvpPresenter<V> {
    fun loadTransactions()
}
