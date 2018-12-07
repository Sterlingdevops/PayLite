package com.sterlingng.paylite.ui.transactions.insights

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class InsightsPresenter<V : InsightsMvpView> @Inject
internal constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), InsightsMvpContract<V>{
    override fun onViewInitialized() {
        super.onViewInitialized()
        mvpView.initView(dataManager.mockPaymentCategories())
    }
}