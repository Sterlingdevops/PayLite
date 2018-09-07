package com.sterlingng.paylite.ui.transactions

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.MvpView

interface TransactionsMvpView : MvpView {
    fun onGetUserTransactionsFailed(response: Response)
    fun onGetUserTransactionsSuccessful(response: Response)
}
