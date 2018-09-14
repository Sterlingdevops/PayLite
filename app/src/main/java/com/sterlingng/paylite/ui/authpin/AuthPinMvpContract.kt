package com.sterlingng.paylite.ui.authpin

import com.sterlingng.paylite.ui.base.MvpPresenter

interface AuthPinMvpContract<V : AuthPinMvpView> : MvpPresenter<V>{
    fun savePin(pinText: String)
    fun validate(pinText: String): Boolean
}