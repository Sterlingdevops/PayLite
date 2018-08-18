package com.sterlingng.paylite.ui.login

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.MvpView

interface LogInMvpView : MvpView {
    fun onDoSignInSuccessful(response: Response)
    fun onDoSignInFailed(throwable: Throwable)
}
