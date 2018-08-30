package com.sterlingng.paylite.ui.signup.otp

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.MvpView

interface OtpMvpView : MvpView {
    fun onValidateOtpFailed(it: Response)
    fun onValidateOtpSuccessful(it: Response)
}