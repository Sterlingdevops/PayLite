package com.sterlingng.paylite.ui.signup.complete

import com.sterlingng.paylite.ui.base.MvpPresenter

interface CompleteMvpContract<V : CompleteMvpView> : MvpPresenter<V> {
    fun initView()
}
