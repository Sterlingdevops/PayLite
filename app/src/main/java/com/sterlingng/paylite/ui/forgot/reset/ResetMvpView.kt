package com.sterlingng.paylite.ui.forgot.reset

import com.sterlingng.paylite.ui.base.MvpView

interface ResetMvpView : MvpView {
    fun onUpdatePasswordFailed()
    fun onUpdatePasswordSuccessful()
}
