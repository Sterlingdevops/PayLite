package com.sterlingng.paylite.ui.login

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.asByteArray
import com.sterlingng.paylite.utils.asHexString
import com.sterlingng.paylite.utils.encryptAES
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject
import kotlin.collections.set


class LogInPresenter<V : LogInMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), LogInMvpContract<V> {

    override fun onViewInitialized() {
        super.onViewInitialized()
        if (dataManager.getCurrentUser()?.email?.length!! > 0) {
            mvpView.onDoSignInSuccessful()
        }
    }

    /**
     * Try to login, checking and catching any server errors that could occur,
     * then fail silently and inform the user with a helpful message
     */
    override fun doLogIn(data: HashMap<String, String>) {
        val initializationVector = "j+N89vDbHBf8+CE2WkdEtw==" //: String = RandomString().nextString()

        data["username"] = "bsrtukpe@gmail.com" //dataManager.getCurrentUser()?.email!!

        val username: String = (data["username"] as String).encryptAES(initializationVector).asByteArray().asHexString()
        val password: String = (data["password"] as String).encryptAES(initializationVector).asByteArray().asHexString()

        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.signin(username, password, initializationVector, "password")
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