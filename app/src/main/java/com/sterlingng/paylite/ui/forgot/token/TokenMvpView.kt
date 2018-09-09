package com.sterlingng.paylite.ui.forgot.token

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.MvpView

interface TokenMvpView : MvpView {
    fun onValidateOtpFailed(it: Response)
    fun onValidateOtpSuccessful(it: Response)
}
