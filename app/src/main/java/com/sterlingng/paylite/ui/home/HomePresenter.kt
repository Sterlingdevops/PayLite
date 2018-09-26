package com.sterlingng.paylite.ui.home

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomePresenter<V : HomeMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), HomeMvpContract<V> {

    override fun onViewInitialized() {
        super.onViewInitialized()
        mvpView.initView(dataManager.getCurrentUser())
    }

    override fun loadCachedWallet() {
        mvpView.onGetWalletSuccessful(dataManager.getWallet()!!)
    }
}