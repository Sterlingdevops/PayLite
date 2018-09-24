package com.sterlingng.paylite.ui.settings

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SettingsPresenter<V : SettingsMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SettingsMvpContract<V> {

    override fun logOut() {
        dataManager.deleteAllWallets()
        mvpView.onLogOutComplete()
    }
}