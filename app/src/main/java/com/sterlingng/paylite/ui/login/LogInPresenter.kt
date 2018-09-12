package com.sterlingng.paylite.ui.login

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

class LogInPresenter<V : LogInMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), LogInMvpContract<V> {

    /**
     * Try to login, checking and catching any server errors that could occur,
     * then fail silently and inform the user with a helpful message
     */
    override fun doLogIn(data: HashMap<String, String>) {
        data["username"] = "bsrtukpe@gmail.com" //dataManager.getCurrentUser()?.email!!
        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.signin(data["username"]!!, data["password"]!!, "password")
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            val user = User()
                            user.email = it["Email"] as String
                            user.lastName = it["LastName"] as String
                            user.phoneNumber = it["Mobile"] as String
                            user.firstName = it["FirstName"] as String
                            user.accessToken = it["access_token"] as String
                            dataManager.saveUser(user)
                            mvpView.onDoSignInSuccessful()
                            mvpView.hideLoading()
                        }) {
                            mvpView.onDoSignInFailed()
                            mvpView.hideLoading()
                        }
        )
    }
}