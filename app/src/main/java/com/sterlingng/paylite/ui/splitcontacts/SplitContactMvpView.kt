package com.sterlingng.paylite.ui.splitcontacts

import com.sterlingng.paylite.ui.base.MvpView

interface SplitContactMvpView : MvpView {
    fun onSplitPaymentFailed()
    fun onSplitPaymentSuccessful()
    fun logout()
}