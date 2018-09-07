package com.sterlingng.paylite.ui.transactions

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface TransactionsMvpContract<V : TransactionsMvpView> : MvpPresenter<V> {
    fun getUserTransactions()
}
