package com.sterlingng.paylite.ui.splitamount

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SplitAmountPresenter<V : SplitAmountMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SplitAmountMvpContract<V>
{
    override fun onViewInitialized() {
        super.onViewInitialized()
        mvpView.initView(dataManager.getWallet())
    }
}