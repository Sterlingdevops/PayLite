package com.sterlingng.paylite.ui.payment

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.PaymentMethod
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PaymentPresenter<V : PaymentMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), PaymentMvpContract<V> {

    override fun setCardDefault(paymentMethod: PaymentMethod) {
        dataManager.setCardDefault(paymentMethod.asCard(true))
    }

    override fun setBankDefault(paymentMethod: PaymentMethod) {
        dataManager.setBankDefault(paymentMethod.asBank(true))
    }

    override fun loadPaymentMethods() {
        val banks = dataManager.getBanks()
        val cards = dataManager.getCards()
        val paymentMethods =
                cards.map { it.asPaymentMethod() } as ArrayList<PaymentMethod>
        paymentMethods.addAll(banks.map { it.asPaymentMethod() })
        mvpView.updatePaymentMethods(paymentMethods)
    }
}