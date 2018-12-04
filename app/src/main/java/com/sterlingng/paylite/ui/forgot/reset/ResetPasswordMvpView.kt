package com.sterlingng.paylite.ui.forgot.reset

import com.sterlingng.paylite.ui.base.MvpView

interface ResetPasswordMvpView : MvpView {
    fun onUpdatePasswordFailed()
    fun onUpdatePasswordSuccessful()
    fun logout()
}
