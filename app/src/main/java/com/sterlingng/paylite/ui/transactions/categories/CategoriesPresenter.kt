package com.sterlingng.paylite.ui.transactions.categories

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CategoriesPresenter<V : CategoriesMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), CategoriesMvpContract<V> {

    override fun loadMockTransactions() {
        mvpView.showLoading()
        mvpView.updateCategories(dataManager.mockTransactions().sortedByDescending { it.date.time })
        mvpView.hideLoading()
    }
}
