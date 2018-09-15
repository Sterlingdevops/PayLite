package com.sterlingng.paylite.ui.signup.pin

import com.sterlingng.paylite.ui.base.MvpPresenter

interface PinMvpContract<V : PinMvpView> : MvpPresenter<V> {
    fun doSignUp(data: HashMap<String, Any>)
}