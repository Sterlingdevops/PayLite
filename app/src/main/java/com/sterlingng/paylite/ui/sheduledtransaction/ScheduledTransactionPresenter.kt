package com.sterlingng.paylite.ui.sheduledtransaction

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.ScheduledPayment
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ScheduledTransactionPresenter<V : ScheduledTransactionMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), ScheduledTransactionMvpContract<V> {

    override fun getTransactions(account: String, ref: String) =
            mvpView.initView(dataManager.getTransactions().filter { it.type == 4 && it.recipientName == account && it.reference.contains(ref) } as ArrayList<Transaction>)

    override fun getUserRelativeTransactions(secondaryAcct: String) {
        mvpView.showLoading()
        dataManager.getUserRelativeTransactions(dataManager.getCurrentUser()?.phoneNumber!!,
                secondaryAcct, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}")
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onGetUserRelativeTransactionsSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onGetUserRelativeTransactionsFailed()
                        mvpView.hideLoading()
                    }

                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }
                })
    }

    override fun cancelStandingOrder(scheduledPayment: ScheduledPayment) {
        mvpView.showLoading()
        dataManager.deleteSchedulePayments(dataManager.getCurrentUser()?.phoneNumber!!, scheduledPayment.reference.toString(), "Bearer ${dataManager.getCurrentUser()?.accessToken!!}")
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onRequestSuccessful(response: Response, message: String) {
                        dataManager.deleteScheduledPayment(scheduledPayment)
                        mvpView.onDeleteScheduledPaymentsSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onDeleteScheduledPaymentsFailed()
                        mvpView.hideLoading()
                    }

                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }
                })
    }
}
