package com.sterlingng.paylite.ui.signup.complete

import com.sterlingng.paylite.ui.base.MvpView

interface CompleteMvpView : MvpView {
    fun setWelcomeText()
    fun onAccessTokenSuccessful()
    fun onAccessTokenFailed()
}