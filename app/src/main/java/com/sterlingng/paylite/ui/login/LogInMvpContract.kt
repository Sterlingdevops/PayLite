package com.sterlingng.paylite.ui.login

import com.sterlingng.paylite.ui.base.MvpPresenter

interface LogInMvpContract<V : LogInMvpView> : MvpPresenter<V> {
    fun doLogIn(email: String, password: String)
    fun doForgotPassword()
}