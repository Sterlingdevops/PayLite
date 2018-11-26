package com.sterlingng.paylite.ui.editprofile

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface EditMvpContract<V : EditMvpView> : MvpPresenter<V> {
    fun updateUserDetails(data: HashMap<String, Any>)
}
