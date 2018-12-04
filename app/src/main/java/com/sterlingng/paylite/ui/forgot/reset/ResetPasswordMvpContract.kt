package com.sterlingng.paylite.ui.forgot.reset

import com.sterlingng.paylite.ui.base.MvpPresenter

interface ResetPasswordMvpContract<V : ResetPasswordMvpView> : MvpPresenter<V> {
    fun resetPassword(data: HashMap<String, Any>)
}