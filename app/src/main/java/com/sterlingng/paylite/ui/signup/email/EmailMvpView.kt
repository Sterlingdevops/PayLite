package com.sterlingng.paylite.ui.signup.email

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.MvpView

interface EmailMvpView : MvpView {
    fun onSendOTPFailed(it: Response)
    fun onSendOTPSuccessful(it: Response)
}