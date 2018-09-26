package com.sterlingng.paylite.ui.dashboard

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

/**
 * Created by rtukpe on 21/03/2018.
 */

@PerActivity
interface DashboardMvpContract<V : DashboardMvpView> : MvpPresenter<V> {
    fun loadBanks()
    fun loadWallet()
}
