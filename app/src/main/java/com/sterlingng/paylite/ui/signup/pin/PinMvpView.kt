package com.sterlingng.paylite.ui.signup.pin

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.MvpView

interface PinMvpView : MvpView {
    fun onDoSignUpSuccessful(response: Response)
    fun onDoSignUpFailed(response: Response)
}