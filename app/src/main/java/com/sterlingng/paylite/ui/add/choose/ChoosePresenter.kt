package com.sterlingng.paylite.ui.add.choose

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ChoosePresenter<Z : ChooseMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<Z>(dataManager, schedulerProvider, compositeDisposable), ChooseMvpContract<Z>
