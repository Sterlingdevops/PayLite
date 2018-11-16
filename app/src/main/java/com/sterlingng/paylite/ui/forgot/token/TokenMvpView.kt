package com.sterlingng.paylite.ui.forgot.token

import com.sterlingng.paylite.ui.base.MvpView

interface TokenMvpView : MvpView {
    fun onValidateOtpFailed()
    fun onValidateOtpSuccessful()
    fun logout()
}
