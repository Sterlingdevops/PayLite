package com.sterlingng.paylite.ui.editprofile

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

class EditProfilePresenter<V : EditProfileMvpView> @Inject
internal constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), EditProfileMvpContract<V> {
    override fun onViewInitialized() {
        super.onViewInitialized()
        dataManager.getCurrentUser()?.let { mvpView.initView(it) }
    }

    override fun updateUserDetails(data: HashMap<String, Any>) {
        val user = dataManager.getCurrentUser()
        data["Mobile"] = user?.phoneNumber!!
        mvpView.showLoading()
        dataManager.updateUserDetails(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onRequestSuccessful(response: Response, message: String) {
                        val user1 = User()
                        user1.phoneNumber = data["Mobile"] as String
                        user1.firstName = data["FirstName"] as String
                        user1.lastName = data["LastName"] as String
                        user1.email = data["Email"] as String
                        dataManager.saveUser(user1)
                        mvpView.onUpdateUserDetailsSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onUpdateUserDetailsFailed()
                        mvpView.hideLoading()
                    }

                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }
                })
    }
}