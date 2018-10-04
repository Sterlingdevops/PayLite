package com.sterlingng.paylite.ui.request

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RequestPresenter<V : RequestMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), RequestMvpContract<V> {

    override fun onViewInitialized() {
        super.onViewInitialized()
        dataManager.getCurrentUser()?.let { mvpView.initView(it) }
    }
}
