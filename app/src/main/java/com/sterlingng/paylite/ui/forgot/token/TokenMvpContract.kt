package com.sterlingng.paylite.ui.forgot.token

import com.sterlingng.paylite.ui.base.MvpPresenter

interface TokenMvpContract<V : TokenMvpView> : MvpPresenter<V> {
    fun validateOtp(data: HashMap<String, Any>)
}