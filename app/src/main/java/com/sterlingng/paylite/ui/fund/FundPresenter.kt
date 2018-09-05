package com.sterlingng.paylite.ui.fund

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.Log
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class FundPresenter<V : FundMvpView> @Inject
internal constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), FundMvpContract<V> {

    override fun fundWallet(data: HashMap<String, Any>) {
        val user = dataManager.getCurrentUser()
        data["username"] = user?.username!!

        mvpView.showLoading()
        compositeDisposable.add(
                dataManager
                        .fundWallet(data)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .onErrorReturn {
                            if (it is java.net.SocketTimeoutException) {
                                val response = Response()
                                response.data = SocketTimeoutException()
                                response.message = "Error!!! The server didn't respond fast enough and the request timed out"
                                response.response = "failed"
                                return@onErrorReturn response
                            } else {
                                val raw = (it as HttpException).response().errorBody()?.string()
                                if (AppUtils.isJSONValid(raw!!)) {
                                    return@onErrorReturn gson.fromJson(raw, Response::class.java)
                                }
                                val response = Response()
                                response.data = HttpException(retrofit2.Response.error<String>(500,
                                        ResponseBody.create(MediaType.parse("text/html; charset=utf-8"), raw)))
                                response.message = "Error!!! The server didn't respond fast enough and the request timed out"
                                response.response = "failed"
                                return@onErrorReturn response
                            }
                        }
                        .subscribe {
                            if (it.response != null && it.response == "00") {
                                val wallet = gson.fromJson(AppUtils.gson.toJson(it.data), Wallet::class.java)
                                dataManager.saveWallet(wallet)
                                mvpView.onFundWalletSuccessful(wallet)
                            } else {
                                mvpView.onFundWalletFailed(it)
                            }
                            mvpView.hideLoading()
                        }
        )
    }

    override fun resolveCardNumber(bin: String) {
        mvpView.showLoading()
        compositeDisposable.add(
                dataManager
                        .resolveCardNumber(bin)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            mvpView.hideLoading()
                            mvpView.onResolveCardNumberSuccessful(it)
                        }) {
                            Log.e(it, "FundPresenter->resolveCardNumber")
                            mvpView.hideLoading()
                            mvpView.onResolveCardNumberFailed(it)
                        }
        )
    }

    override fun resolveAccountNumber(accountNumber: String, bankCode: String) {
        mvpView.showLoading()
        compositeDisposable.add(
                dataManager
                        .resolveBankAccount(accountNumber, bankCode)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            mvpView.hideLoading()
                            mvpView.onResolveAccountNumberSuccessful(it)
                        }) {
                            Log.e(it, "FundPresenter->resolveAccountNumber")
                            mvpView.hideLoading()
                            mvpView.onResolveAccountNumberFailed(it)
                        }
        )
    }

    override fun loadBanks() {
        mvpView.onLoadBanksSuccessful(dataManager.mockBanks())
    }
}