package com.sterlingng.paylite.ui.splitcontacts

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SplitContactPresenter<V : SplitContactMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SplitContactMvpContract<V> {

    override fun onViewInitialized() {
        super.onViewInitialized()
        mvpView.initView(dataManager.getWallet())
    }

    override fun splitPayment(data: HashMap<String, Any>) {
        val user = dataManager.getCurrentUser()
        data["userid"] = user?.phoneNumber!!
        data["Amount"] = data["amount"] as String
        data.remove("amount")

        mvpView.showLoading()
        dataManager
                .splitPayment(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onRequestSuccessful(response: Response, message: String) {
                        val wallet = AppUtils.gson.fromJson(AppUtils.gson.toJson(response.data), Wallet::class.java)
                        dataManager.saveWallet(wallet)
                        mvpView.onSplitPaymentSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onSplitPaymentFailed()
                        mvpView.hideLoading()
                    }

                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }
                })
    }
}