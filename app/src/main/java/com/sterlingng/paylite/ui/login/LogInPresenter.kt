package com.sterlingng.paylite.ui.login

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LogInPresenter<V : LogInMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), LogInMvpContract<V> {

    override fun onViewInitialized() {
        super.onViewInitialized()
        if (dataManager.getCurrentUser() != null) {
            mvpView.initView(dataManager.getCurrentUser()!!)
        }
    }

    override fun doLogIn(data: HashMap<String, Any>) {
        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.signin(data)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            mvpView.hideLoading()
                            val user = gson.fromJson(gson.toJson(it.data), User::class.java)
                            dataManager.saveUser(user)
                            mvpView.onDoSignInSuccessful(it)
                        }) {
                            mvpView.hideLoading()
                            mvpView.onDoSignInFailed(it)
                        }
        )
    }
}