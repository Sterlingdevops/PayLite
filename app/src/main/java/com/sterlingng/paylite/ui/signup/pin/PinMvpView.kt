package com.sterlingng.paylite.ui.signup.pin

import com.sterlingng.paylite.ui.base.MvpView

interface PinMvpView : MvpView {
    fun onDoSignUpSuccessful()
    fun onDoSignUpFailed(failureReason: String)
}