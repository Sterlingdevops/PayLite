package com.sterlingng.paylite.ui.signup.otp

import com.sterlingng.paylite.ui.base.MvpPresenter

interface OtpMvpContract<V : OtpMvpView> : MvpPresenter<V> {
    fun validateOtp(data: HashMap<String, Any>)
}