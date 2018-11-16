package com.sterlingng.paylite.ui.forgot.email

import com.sterlingng.paylite.ui.base.MvpView

interface EmailForgotMvpView : MvpView {
    fun onPasswordResetTokenFailed()
    fun onPasswordResetTokenSuccessful()
    fun logout()
}