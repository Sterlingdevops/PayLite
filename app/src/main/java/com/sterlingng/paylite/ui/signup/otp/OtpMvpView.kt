package com.sterlingng.paylite.ui.signup.otp

import com.sterlingng.paylite.ui.base.MvpView

interface OtpMvpView : MvpView {
    fun onValidateOtpFailed()
    fun onValidateOtpSuccessful()
}