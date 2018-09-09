package com.sterlingng.paylite.ui.forgot.email

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.MvpView

interface EmailForgotMvpView : MvpView {
    fun onPasswordResetTokenFailed(response: Response)
    fun onPasswordResetTokenSuccessful(response: Response)
}