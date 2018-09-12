package com.sterlingng.paylite.ui.fund

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.Log
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject
import kotlin.collections.set


class FundPresenter<V : FundMvpView> @Inject
internal constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), FundMvpContract<V> {

    override fun loadCachedWallet() {
        dataManager.getWallet()?.let { mvpView.initView(it) }
    }

    override fun loadWallet() {
        mvpView.showLoading()
        val user = dataManager.getCurrentUser()

        compositeDisposable.add(
                dataManager.getWallet(user?.phoneNumber!!, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}")
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
                                val wallet = gson.fromJson(gson.toJson(it.data), Wallet::class.java)
                                dataManager.saveWallet(wallet)
                                mvpView.onGetWalletSuccessful(wallet)
                            } else {
                                mvpView.onGetWalletFailed(it)
                            }
                            mvpView.hideLoading()
                        }
        )
    }

    override fun fundWalletWithCard(data: HashMap<String, Any>) {
        dataManager.getCurrentUser()?.phoneNumber?.let { data["mobile"] = it }
        dataManager.getWallet()?.walletId?.let { data["customerId"] = it }

        mvpView.showLoading()
        compositeDisposable.add(
                dataManager
                        .fundWalletWithCard(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
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
                                mvpView.onFundWalletSuccessful()
                            } else {
                                mvpView.onFundWalletFailed(it)
                            }
                            mvpView.hideLoading()
                        }
        )
    }

    override fun fundWalletWithBankAccount(data: HashMap<String, Any>) {
        val user = dataManager.getCurrentUser()
        data["toacct"] = user?.phoneNumber!!

//        val base_string = gson.toJson(data)
//        var hash = ""
//        try {
//            val mac = Mac.getInstance("HmacSHA256")
//            val secret = SecretKeySpec(SECRET_KEY.fromBase64(), mac.algorithm)
//            mac.init(secret)
//
//            val digest = mac.doFinal(base_string.toByteArray())
//            hash = Base64.encodeToString(digest, Base64.NO_WRAP)
//        } catch (e: Exception) {
//            Log.e(e.localizedMessage)
//        }

        mvpView.showLoading()
        compositeDisposable.add(
                dataManager
                        .fundWalletWithBankAccount(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
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
                                mvpView.onFundWalletSuccessful()
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
                            if (it.status) {
                                mvpView.hideLoading()
                                mvpView.onResolveCardNumberSuccessful(it)
                            } else {
                                Log.e(it.toString())
                                mvpView.hideLoading()
                                mvpView.onResolveCardNumberFailed(it)
                            }
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