package com.sterlingng.paylite.ui.help

import com.sterlingng.paylite.ui.base.MvpPresenter

interface HelpMvpContract<V : HelpMvpView> : MvpPresenter<V> {
    fun getUserName(): String
    fun getUserPhone(): String
}
