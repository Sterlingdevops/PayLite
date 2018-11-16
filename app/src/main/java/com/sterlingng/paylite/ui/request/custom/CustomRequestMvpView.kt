package com.sterlingng.paylite.ui.request.custom

import com.sterlingng.paylite.ui.base.MvpView

interface CustomRequestMvpView : MvpView {
    fun onRequestPaymentLinkSent()
    fun onSendRequestPaymentLinkFailed()
    fun logout()
}