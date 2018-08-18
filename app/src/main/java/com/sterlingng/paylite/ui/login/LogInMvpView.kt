package com.sterlingng.paylite.ui.login

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.ui.base.MvpView

interface LogInMvpView : MvpView {
    fun initView(currentUser: User)
    fun onDoSignInSuccessful(response: Response)
    fun onDoSignInFailed(throwable: Throwable)
}
