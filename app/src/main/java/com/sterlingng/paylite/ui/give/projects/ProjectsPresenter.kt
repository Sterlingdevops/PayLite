package com.sterlingng.paylite.ui.give.projects

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProjectsPresenter<V : ProjectsMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), ProjectsMvpContract<V> {

    override fun loadProjects() {
        mvpView.showLoading()
        mvpView.updateProjects(dataManager.mockProjects())
        mvpView.hideLoading()
    }
}