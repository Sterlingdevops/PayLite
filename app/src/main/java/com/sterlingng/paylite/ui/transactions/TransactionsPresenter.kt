package com.sterlingng.paylite.ui.transactions

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TransactionsPresenter<V : TransactionsMvpView> @Inject
internal constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), TransactionsMvpContract<V> {

    override fun onViewInitialized() {
        super.onViewInitialized()
        dataManager.getWallet()?.let { mvpView.initView(it) }
    }
}