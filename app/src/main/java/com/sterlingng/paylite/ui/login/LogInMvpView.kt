package com.sterlingng.paylite.ui.login

import com.sterlingng.paylite.ui.base.MvpView

interface LogInMvpView : MvpView {
    fun onDoSignInSuccessful()
    fun onDoSignInFailed()
    fun onUserNotRegistered()
}
