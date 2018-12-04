package com.sterlingng.paylite.ui.forgot.email

import com.sterlingng.paylite.ui.base.MvpPresenter

interface ForgotPhoneMvpContract<V : ForgotPhoneMvpView> : MvpPresenter<V> {
    fun sendPasswordResetToken(data: HashMap<String, Any>)
}