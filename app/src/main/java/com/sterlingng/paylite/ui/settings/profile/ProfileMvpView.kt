package com.sterlingng.paylite.ui.settings.profile

import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.ui.base.MvpView

interface ProfileMvpView : MvpView {
    fun logout()
    fun initView(user: User)
}
