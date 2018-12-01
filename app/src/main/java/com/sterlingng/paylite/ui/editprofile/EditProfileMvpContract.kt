package com.sterlingng.paylite.ui.editprofile

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface EditProfileMvpContract<V : EditProfileMvpView> : MvpPresenter<V> {
    fun updateUserDetails(data: HashMap<String, Any>)
}
