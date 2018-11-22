package com.sterlingng.paylite.ui.complete

import com.sterlingng.paylite.ui.base.MvpView

interface CompleteMvpView : MvpView {
    fun setWelcomeText()
    fun onAccessTokenSuccessful()
    fun onAccessTokenFailed()
}