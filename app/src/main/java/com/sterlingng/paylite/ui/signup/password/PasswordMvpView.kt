package com.sterlingng.paylite.ui.signup.password

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.MvpView

interface PasswordMvpView : MvpView {
    fun onDoSignUpSuccessful(response: Response)
    fun onDoSignUpFailed(response: Response)
}