package com.sterlingng.paylite.ui.signup.phone

import com.sterlingng.paylite.ui.base.MvpPresenter

interface PhoneMvpContract<V : PhoneMvpView> : MvpPresenter<V> {
    fun sendOtp(data: HashMap<String, Any>)
}