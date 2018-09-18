package com.sterlingng.paylite.ui.request.custom

import com.sterlingng.paylite.ui.base.MvpPresenter

interface CustomRequestMvpContract<V : CustomRequestMvpView> : MvpPresenter<V> {
    fun requestPaymentLink(data: HashMap<String, Any>)
}