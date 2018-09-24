package com.sterlingng.paylite.ui.splitcontacts

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.MvpView

interface SplitContactMvpView : MvpView {
    fun onSplitPaymentFailed(response: Response)
    fun onSplitPaymentSuccessful()
    fun logout()
}