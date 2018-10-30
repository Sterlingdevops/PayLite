package com.sterlingng.paylite.ui.transactions.detail

import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.ui.base.MvpView

interface TransactionDetailMvpView : MvpView {
    fun initView(transactions: ArrayList<Transaction>)
}