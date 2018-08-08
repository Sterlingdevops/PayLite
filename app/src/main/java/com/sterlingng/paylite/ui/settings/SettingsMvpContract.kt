package com.sterlingng.paylite.ui.settings

import com.sterlingng.paylite.ui.base.MvpPresenter

interface SettingsMvpContract<V : SettingsMvpView> : MvpPresenter<V> {
    fun logOut()
}