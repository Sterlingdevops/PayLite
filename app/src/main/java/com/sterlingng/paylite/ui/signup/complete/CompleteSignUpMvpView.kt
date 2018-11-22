package com.sterlingng.paylite.ui.signup.complete

import com.sterlingng.paylite.ui.base.MvpView

interface CompleteSignUpMvpView : MvpView {
    fun setWelcomeText()
    fun onAccessTokenSuccessful()
    fun onAccessTokenFailed()
}