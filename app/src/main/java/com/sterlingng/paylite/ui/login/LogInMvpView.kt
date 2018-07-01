package com.sterlingng.paylite.ui.login

import android.view.View
import com.sterlingng.paylite.ui.base.MvpView

interface LogInMvpView : MvpView {
    fun onLogInClicked(view: View)
    fun onForgotPasswordClicked(view: View)
}
