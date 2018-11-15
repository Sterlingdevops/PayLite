package com.sterlingng.paylite.ui.transactions.paymentcategory

import com.sterlingng.paylite.ui.base.MvpPresenter

interface PaymentCategoriesMvpContract<V : PaymentCategoriesMvpView> : MvpPresenter<V> {
    fun updateTransactionCategory(data: HashMap<String, Any>)
}