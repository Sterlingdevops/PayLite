package com.sterlingng.paylite.ui.paymentcategory

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PaymentCategoriesPresenter<V : PaymentCategoriesMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), PaymentCategoriesMvpContract<V> {

    override fun loadTransactions() {

    }

    override fun onViewInitialized() {
        super.onViewInitialized()
        mvpView.initView(dataManager.mockPaymentCategories())
    }
}
