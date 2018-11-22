package com.sterlingng.paylite.ui.complete

import com.sterlingng.paylite.ui.base.MvpPresenter

interface CompleteMvpContract<V : CompleteMvpView> : MvpPresenter<V> {
    fun initView()
}
