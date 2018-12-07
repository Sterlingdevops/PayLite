package com.sterlingng.paylite.ui.forgot.reset

import com.google.gson.reflect.TypeToken
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ResetPasswordPresenter<V : ResetPasswordMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), ResetPasswordMvpContract<V> {

    override fun resetPassword(data: HashMap<String, Any>) {
        mvpView.showLoading()
        dataManager.updateForgotPassword(data, gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        dataManager.deleteAll()

                        val dataType = object : TypeToken<HashMap<String, Any>>() {}.type
                        val d = gson.fromJson<HashMap<String, Any>>(gson.toJson(response.data), dataType)

                        val user = User()
                        user.accessToken = ""
                        user.email = d["Email"] as String
                        user.lastName = d["LastName"] as String
                        user.phoneNumber = d["Mobile"] as String
                        user.firstName = d["FirstName"] as String

                        dataManager.saveUser(user)
                        mvpView.onUpdatePasswordSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onUpdatePasswordFailed()
                        mvpView.hideLoading()
                    }
                })
    }
}