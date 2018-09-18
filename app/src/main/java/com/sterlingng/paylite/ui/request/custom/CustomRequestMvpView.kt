package com.sterlingng.paylite.ui.request.custom

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.MvpView

interface CustomRequestMvpView : MvpView {
    fun onRequestPaymentLinkSent(response: Response)
    fun onSendRequestPaymentLinkFailed(response: Response)
    fun logout()
}