package com.sterlingng.paylite.ui.scheduled

import com.google.gson.reflect.TypeToken
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.ScheduledPayment
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ScheduledPresenter<V : ScheduledMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), ScheduledMvpContract<V> {

    override fun onViewInitialized() {
        super.onViewInitialized()
        dataManager.getWallet()?.let { mvpView.initView(it, dataManager.getScheduledPayments()) }
    }

    override fun loadScheduledPayments() {
        dataManager
                .getSchedulePayments(dataManager.getCurrentUser()?.phoneNumber!!, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}")
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onRequestSuccessful(response: Response, message: String) {
                        val type = object : TypeToken<ArrayList<ScheduledPayment>>() {}.type
                        val payments: ArrayList<ScheduledPayment> = gson.fromJson(gson.toJson(response.data), type)
                        dataManager.saveScheduledPayments(payments)
                        mvpView.onScheduledPaymentsSuccessful(payments)
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onScheduledPaymentsFailed()
                    }

                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }
                })
    }
}