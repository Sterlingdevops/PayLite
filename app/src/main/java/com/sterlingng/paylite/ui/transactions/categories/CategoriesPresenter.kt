package com.sterlingng.paylite.ui.transactions.categories

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class CategoriesPresenter<V : CategoriesMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), CategoriesMvpContract<V> {

    override fun loadCreditCards() {
        val retrySubject = PublishSubject.create<Any>()
        mvpView.showLoading()
        compositeDisposable.add(
                dataManager
                        .mockTransactions()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .doOnError { throwable ->
                            Timber.e(throwable)
                            retrySubject.onNext(Any())
                        }
                        .retryWhen { observable: Observable<Throwable> ->
                            observable.zipWith(retrySubject) { o: Throwable?, _: Any? -> o }
                        }
                        .subscribe {
                            mvpView.updateCategories(it)
                            mvpView.hideLoading()
                        }
        )
    }
}
