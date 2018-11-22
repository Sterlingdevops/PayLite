package com.sterlingng.paylite.ui.signup.pin

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

class PinPresenter<V : PinMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), PinMvpContract<V> {

    override fun doSignUp(data: HashMap<String, Any>) {
        mvpView.showLoading()
        dataManager.signup(data, gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onRequestSuccessful(response: Response, message: String) {
                        val type = object : TypeToken<HashMap<String, Any>>() {}.type
                        val d = gson.fromJson<HashMap<String, Any>>(gson.toJson(response.data), type)

                        val user = User()
                        user.accessToken = ""
                        user.email = d["email"] as String
                        user.lastName = d["lastname"] as String
                        user.phoneNumber = d["mobile"] as String
                        user.firstName = d["firstname"] as String

                        dataManager.saveUser(user)
                        mvpView.onDoSignUpSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onDoSignUpFailed(failureReason)
                        mvpView.hideLoading()
                    }

                    override fun onAuthorizationError() {

                    }
                })
    }
}