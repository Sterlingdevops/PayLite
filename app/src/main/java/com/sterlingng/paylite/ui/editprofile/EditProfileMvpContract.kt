package com.sterlingng.paylite.ui.editprofile

import com.sterlingng.paylite.ui.base.MvpPresenter

interface EditProfileMvpContract<V : EditProfileMvpView> : MvpPresenter<V> {
    fun updateUserDetails(data: HashMap<String, Any>)
}
