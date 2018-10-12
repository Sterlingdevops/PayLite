package com.sterlingng.paylite.ui.profile.edit

import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.ui.base.MvpView

interface EditMvpView : MvpView {
    fun logout()
    fun initView(user: User)
    fun onUpdateUserDetailsFailed()
    fun onUpdateUserDetailsSuccessful()
}
