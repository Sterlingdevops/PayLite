package com.sterlingng.paylite.ui.add.expiry

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ExpiryPresenter<Z : ExpiryMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<Z>(dataManager, schedulerProvider, compositeDisposable), ExpiryMvpContract<Z>
