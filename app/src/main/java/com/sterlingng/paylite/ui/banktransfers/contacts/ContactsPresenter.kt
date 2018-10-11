package com.sterlingng.paylite.ui.banktransfers.contacts

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ContactsPresenter<V : ContactsMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), ContactsMvpContract<V>{

    override fun onViewInitialized() {
        super.onViewInitialized()
        mvpView.initView(dataManager.getContacts())
    }
}