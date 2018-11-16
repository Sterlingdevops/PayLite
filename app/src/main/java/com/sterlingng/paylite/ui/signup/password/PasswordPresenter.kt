package com.sterlingng.paylite.ui.signup.password

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PasswordPresenter<V : PasswordMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), PasswordMvpContract<V> {

    override fun doSignUp(data: HashMap<String, Any>) {
        mvpView.showLoading()
        dataManager.signup(data, gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onRequestSuccessful(response: Response, message: String) {
                        val user = gson.fromJson(AppUtils.gson.toJson(response.data), User::class.java)
                        dataManager.saveUser(user)
                        mvpView.onDoSignUpSuccessful()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onDoSignUpFailed()
                    }

                    override fun onAuthorizationError() {

                    }
                })

    }
}