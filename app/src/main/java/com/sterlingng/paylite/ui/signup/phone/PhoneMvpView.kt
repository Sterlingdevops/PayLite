package com.sterlingng.paylite.ui.signup.phone

import com.sterlingng.paylite.ui.base.MvpView

interface PhoneMvpView : MvpView {
    fun onSendOTPFailed()
    fun onSendOTPSuccessful()
}