package com.sterlingng.paylite.ui.home

import com.sterlingng.paylite.ui.base.MvpPresenter

interface HomeMvpContract<V : HomeMvpView> : MvpPresenter<V> {
    fun loadCachedWallet()
}