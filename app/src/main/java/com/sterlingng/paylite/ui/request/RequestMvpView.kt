package com.sterlingng.paylite.ui.request

import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.ui.base.MvpView

interface RequestMvpView : MvpView {
    fun initView(user: User)
}
