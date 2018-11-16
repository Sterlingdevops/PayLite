package com.sterlingng.paylite.ui.newpaymentamount

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewPaymentAmountPresenter<V : NewPaymentAmountMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), NewPaymentAmountMvpContract<V> {

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
                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onSchedulePaymentSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onSchedulePaymentFailed()
                        mvpView.hideLoading()
                    }

                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }
                })
    }

    override fun sendMoney(data: HashMap<String, Any>) {
        val user = dataManager.getCurrentUser()
        data["Mobile"] = user?.phoneNumber!!
        mvpView.showLoading()
        dataManager.sendMoney(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onRequestSuccessful(response: Response, message: String) {
                        val wallet = gson.fromJson(gson.toJson(response.data), Wallet::class.java)
                        dataManager.saveWallet(wallet)
                        mvpView.onSendMoneySuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onSendMoneyFailed()
                        mvpView.hideLoading()
                    }

                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }
                })
    }

    override fun loadCachedWallet() {
        dataManager.getWallet()?.let { mvpView.initView(it) }
    }
}