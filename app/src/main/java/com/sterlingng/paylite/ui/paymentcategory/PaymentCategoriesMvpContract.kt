package com.sterlingng.paylite.ui.paymentcategory

import com.sterlingng.paylite.ui.base.MvpPresenter

interface PaymentCategoriesMvpContract<V : PaymentCategoriesMvpView> : MvpPresenter<V> {
    fun loadTransactions()
}