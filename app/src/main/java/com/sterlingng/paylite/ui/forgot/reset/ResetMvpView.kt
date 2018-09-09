package com.sterlingng.paylite.ui.forgot.reset

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.MvpView

interface ResetMvpView : MvpView {
    fun onResetPasswordSuccessful(response: Response)
    fun onResetPasswordFailed(response: Response)
}
