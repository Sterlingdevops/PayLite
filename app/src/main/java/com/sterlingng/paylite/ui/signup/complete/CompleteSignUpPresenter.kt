package com.sterlingng.paylite.ui.signup.complete

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.RandomString
import com.sterlingng.paylite.utils.encryptAES
import com.sterlingng.paylite.utils.toBase64String
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

class CompleteSignUpPresenter<V : CompleteSignUpMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), CompleteSignUpMvpContract<V> {

    override fun initView() {
        mvpView.setWelcomeText()
    }

    override fun doLogIn(data: HashMap<String, String>) {
        val initializationVector: String = RandomString(length = 16).nextString()
        val username: String = (data["username"] as String).encryptAES(initializationVector)
        val password: String = (data["password"] as String).encryptAES(initializationVector)

        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.signIn(username, password, initializationVector.toByteArray().toBase64String(), "password")
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
                            mvpView.onAccessTokenSuccessful()
                            mvpView.hideLoading()
                        }) {
                            mvpView.onAccessTokenFailed()
                            mvpView.hideLoading()
                        }
        )
    }
}