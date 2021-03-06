package com.sterlingng.paylite.ui.settings.profile

import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.ui.base.MvpView

interface ProfileMvpView : MvpView {
    fun initView(user: User)
    fun onLogOutComplete()
}
