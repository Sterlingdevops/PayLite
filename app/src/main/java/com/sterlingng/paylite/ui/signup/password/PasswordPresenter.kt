package com.sterlingng.paylite.ui.signup.password

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.Log
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

class PasswordPresenter<V : PasswordMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), PasswordMvpContract<V> {

    override fun doSignUp(data: HashMap<String, Any>) {
        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.signup(data)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            mvpView.hideLoading()
                            val user = gson.fromJson(AppUtils.gson.toJson(it.data), User::class.java)
                            dataManager.saveUser(user)
                            mvpView.onDoSignUpSuccessful(it)
                        }) {
                            (it as HttpException)
                            Log.e(it, "PasswordPresenter->onDoSignUpFailed")
                            if (it.response().errorBody()?.string() != null && !it.response().errorBody()?.string()?.isEmpty()!!) {
                                val raw = it.response().errorBody()?.string()!!
                                val response = gson.fromJson(raw, Response::class.java)
                                mvpView.onDoSignUpFailed(response)
                            }
                            mvpView.hideLoading()
                        }
        )
    }
}