package com.sterlingng.paylite.ui.send

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SendMoneyPresenter<V : SendMoneyMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SendMoneyMvpContract<V> {

    override fun deleteContact(contact: PayliteContact) {
        dataManager.deleteContact(contact)
        mvpView.updateContacts(dataManager.getContacts())
    }

    override fun loadCachedWallet() {
        mvpView.initView(dataManager.getWallet(), dataManager.getScheduledPayments().size.toString())
    }

    override fun loadContacts() {
        mvpView.updateContacts(dataManager.getContacts())
    }
}