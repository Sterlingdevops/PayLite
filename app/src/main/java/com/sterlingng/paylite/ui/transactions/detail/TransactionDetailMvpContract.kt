package com.sterlingng.paylite.ui.transactions.detail

import com.sterlingng.paylite.ui.base.MvpPresenter

interface TransactionDetailMvpContract<V : TransactionDetailMvpView> : MvpPresenter<V> {
    fun getTransactions()
}