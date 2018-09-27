package com.sterlingng.paylite.ui.newpaymentamount

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class NewPaymentAmountPresenter<V : NewPaymentAmountMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), NewPaymentAmountMvpContract<V> {

    override fun saveContact(contact: PayliteContact) {
        dataManager.saveContact(contact)
    }

    override fun sendMoney(data: HashMap<String, Any>) {
        val user = dataManager.getCurrentUser()
        data["Mobile"] = user?.phoneNumber!!

        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.sendMoney(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
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
                                    val response = gson.fromJson(raw, Response::class.java)
                                    response.code = it.code()
                                    return@onErrorReturn response
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
//                                val type = object : TypeToken<HashMap<String, Any>>() {}.type
//                                val d = gson.fromJson<HashMap<String, Any>>(gson.toJson(it.data), type)
//                                val wallet = Wallet()
//                                wallet.walletId = d["CustomerID"] as String
//                                wallet.balance = d["AvailableBalance"] as Number
//                                wallet.name = d["nuban"] as String
                                dataManager.saveWallet(wallet)
                                mvpView.hideLoading()
                                mvpView.onSendMoneySuccessful(wallet)
                            } else {
                                if (it.code == 401) {
                                    mvpView.logout()
                                } else {
                                    mvpView.hideLoading()
                                    mvpView.onSendMoneyFailed(it)
                                }
                            }
                        }
        )
    }

    override fun loadCachedWallet() {
        mvpView.initView(dataManager.getWallet())
    }
}