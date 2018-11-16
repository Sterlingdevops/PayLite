package com.sterlingng.paylite.ui.banktransfers.banktransferamount

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class BankTransferAmountPresenter<V : BankTransferAmountMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), BankTransferAmountMvpContract<V> {

    override fun saveContact(contact: PayliteContact) {
        dataManager.saveContact(contact)
    }

    override fun schedulePayment(data: java.util.HashMap<String, Any>) {
        val user = dataManager.getCurrentUser()
        user?.phoneNumber?.let { data["UserID"] = it }

        mvpView.showLoading()
        dataManager.schedulePayments(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onSchedulePaymentSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onSchedulePaymentFailed()
                        mvpView.hideLoading()
                    }
                })
    }

    override fun bankTransferSterling(data: HashMap<String, Any>) {
        val user = dataManager.getCurrentUser()
        data["PhoneNumber"] = user?.phoneNumber!!

        mvpView.showLoading()
        dataManager.cashoutToSterlingBankAccount(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onBankTransferSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onBankTransferFailed()
                        mvpView.hideLoading()
                    }
                })
    }

    override fun bankTransfer(data: HashMap<String, Any>) {
        val user = dataManager.getCurrentUser()
        data["PhoneNumber"] = user?.phoneNumber!!

        mvpView.showLoading()
        dataManager.cashoutToBankAccount(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onBankTransferSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onBankTransferFailed()
                        mvpView.hideLoading()
                    }
                })
    }

    override fun loadCachedWallet() {
        dataManager.getWallet()?.let { mvpView.initView(it) }
    }
}