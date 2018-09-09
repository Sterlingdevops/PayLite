package com.sterlingng.paylite.ui.forgot.reset

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ResetPresenter<V : ResetMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), ResetMvpContract<V> {

    override fun resetPassword(data: HashMap<String, Any>) {
        mvpView.showLoading()
//        compositeDisposable.add(
//                dataManager.forgotPassword(data)
//                        .subscribeOn(schedulerProvider.io())
//                        .observeOn(schedulerProvider.ui())
//                        .subscribe({
//                            mvpView.onResetPasswordSuccessful(it)
//                            mvpView.hideLoading()
//                        }) {
//                            mvpView.hideLoading()
//                            mvpView.onResetPasswordFailed(it)
//                        }
//        )
    }
}