package com.sterlingng.paylite.ui.editprofile

import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.ui.base.MvpView

interface EditProfileMvpView : MvpView {
    fun logout()
    fun initView(user: User)
    fun onUpdateUserDetailsFailed()
    fun onUpdateUserDetailsSuccessful()
}
