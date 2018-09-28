package com.sterlingng.paylite.ui.banktransfers

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class BankTransferPresenter<V : BankTransferMvpView> @Inject
internal constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), BankTransferMvpContract<V> {

    override fun loadWallet() {
        dataManager.getWallet()?.let { mvpView.initView(it) }
    }
}