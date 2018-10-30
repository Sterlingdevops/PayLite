package com.sterlingng.paylite.ui.transactions.detail

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TransactionDetailPresenter<V : TransactionDetailMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), TransactionDetailMvpContract<V> {

    override fun getTransactions() {
        mvpView.initView(dataManager.getTransactions())
    }
}