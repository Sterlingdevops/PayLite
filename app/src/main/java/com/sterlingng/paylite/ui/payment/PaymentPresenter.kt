package com.sterlingng.paylite.ui.payment

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PaymentPresenter<V : PaymentMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), PaymentMvpContract<V> {

    override fun loadMockPaymentMethods() {
        mvpView.showLoading()
        mvpView.updatePaymentMethods(dataManager.mockPaymentMethods())
        mvpView.hideLoading()
    }
}