package com.sterlingng.paylite.ui.forgot.email

import com.sterlingng.paylite.ui.base.MvpPresenter

interface EmailForgotMvpContract<V : EmailForgotMvpView> : MvpPresenter<V> {
    fun sendPasswordResetToken(data: HashMap<String, Any>)
}