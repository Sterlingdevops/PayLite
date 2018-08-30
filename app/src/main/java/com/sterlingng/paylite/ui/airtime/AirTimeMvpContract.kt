package com.sterlingng.paylite.ui.airtime

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface AirTimeMvpContract<V : AirTimeMvpView> : MvpPresenter<V> {
    fun loadCachedWallet()
}
