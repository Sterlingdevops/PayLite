package com.sterlingng.paylite.ui.transactions.categories

import com.sterlingng.paylite.ui.base.MvpPresenter

interface TransactionCategoriesMvpContract<V : TransactionCategoriesMvpView> : MvpPresenter<V> {
    fun loadTransactions()
}
