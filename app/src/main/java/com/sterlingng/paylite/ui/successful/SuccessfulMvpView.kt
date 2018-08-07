package com.sterlingng.paylite.ui.successful

import com.sterlingng.paylite.data.model.PaymentCategory
import com.sterlingng.paylite.ui.base.MvpView

interface SuccessfulMvpView : MvpView {
    fun updateCategories(categories: ArrayList<PaymentCategory>)
}
