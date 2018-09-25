package com.sterlingng.paylite.ui.newpayment

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewPaymentPresenter<V : NewPaymentMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), NewPaymentMvpContract<V> {

    override fun loadCachedWallet() {
        mvpView.initView(dataManager.getWallet())
    }
}