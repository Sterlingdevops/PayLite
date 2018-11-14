package com.sterlingng.paylite.ui.successful

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SuccessfulPresenter<V : SuccessfulMvpView> @Inject
internal constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SuccessfulMvpContract<V> {

    override fun loadCategories() {
        mvpView.updateCategories(dataManager.mockPaymentCategories())
    }
}