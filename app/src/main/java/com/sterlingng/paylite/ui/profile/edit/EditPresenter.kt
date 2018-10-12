package com.sterlingng.paylite.ui.profile.edit

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class EditPresenter<V : EditMvpView> @Inject
internal constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), EditMvpContract<V> {
    override fun onViewInitialized() {
        super.onViewInitialized()
        dataManager.getCurrentUser()?.let { mvpView.initView(it) }
    }

    override fun updateUserDetails(data: HashMap<String, Any>) {
        val user = dataManager.getCurrentUser()
        data["Mobile"] = user?.phoneNumber!!

        mvpView.showLoading()
        compositeDisposable.add(
                dataManager
                        .updateUserDetails(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", AppUtils.gson.toJson(data).sha256())
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
                                    val response = AppUtils.gson.fromJson(raw, Response::class.java)
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
                                val user1 = User()
                                user1.phoneNumber = data["Mobile"] as String
                                user1.firstName = data["FirstName"] as String
                                user1.lastName = data["LastName"] as String
                                user1.email = data["Email"] as String
                                dataManager.saveUser(user1)

                                mvpView.onUpdateUserDetailsSuccessful()
                            } else {
                                if (it.code == 401) {
                                    mvpView.logout()
                                } else {
                                    mvpView.onUpdateUserDetailsFailed()
                                }
                            }
                            mvpView.hideLoading()
                        }
        )
    }
}