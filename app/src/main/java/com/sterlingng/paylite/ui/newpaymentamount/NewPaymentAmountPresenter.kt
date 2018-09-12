package com.sterlingng.paylite.ui.newpaymentamount

import android.util.Base64
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.CRYPTO_ALGO
import com.sterlingng.paylite.utils.SECRET_KEY
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class NewPaymentAmountPresenter<V : NewPaymentAmountMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), NewPaymentAmountMvpContract<V> {

    override fun sendMoney(data: HashMap<String, Any>) {
        val user = dataManager.getCurrentUser()
        data["Mobile"] = user?.phoneNumber!!

        val message = AppUtils.gson.toJson(data)
        val sha256_HMAC = Mac.getInstance(CRYPTO_ALGO)
        val secret_key = SecretKeySpec(SECRET_KEY.toByteArray(), CRYPTO_ALGO)
        sha256_HMAC.init(secret_key)

        val hash = Base64.encodeToString(sha256_HMAC.doFinal(message.toByteArray()), Base64.NO_WRAP)

        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.sendMoney(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", hash)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .onErrorReturn {
                            if (it is SocketTimeoutException) {
                                val response = Response()
                                response.data = SocketTimeoutException()
                                response.message = "Error!!! The server didn't respond fast enough and the request timed out"
                                response.response = "failed"
                                return@onErrorReturn response
                            } else {
                                val raw = (it as HttpException).response().errorBody()?.string()
                                if (AppUtils.isJSONValid(raw!!)) {
                                    return@onErrorReturn AppUtils.gson.fromJson(raw, Response::class.java)
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
                                val wallet = AppUtils.gson.fromJson(AppUtils.gson.toJson(it.data), Wallet::class.java)
                                dataManager.saveWallet(wallet)
                                mvpView.hideLoading()
                                mvpView.onSendMoneySuccessful(wallet)
                            } else {
                                mvpView.hideLoading()
                                mvpView.onSendMoneyFailed(it)
                            }
                        }
        )
    }

    override fun loadCachedWallet() {
        mvpView.initView(dataManager.getWallet())
    }
}