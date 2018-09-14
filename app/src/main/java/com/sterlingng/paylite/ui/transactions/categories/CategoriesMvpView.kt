package com.sterlingng.paylite.ui.transactions.categories

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.ui.base.MvpView

interface CategoriesMvpView : MvpView {
    fun updateCategories(it: Collection<Transaction>)
    fun onGetUserTransactionsFailed(response: Response)
    fun onGetUserTransactionsSuccessful(response: Response)
}
