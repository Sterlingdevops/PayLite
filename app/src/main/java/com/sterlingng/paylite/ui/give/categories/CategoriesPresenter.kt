package com.sterlingng.paylite.ui.give.categories

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class CategoriesPresenter<V : CategoriesMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), CategoriesMvpContract<V> {

    override fun loadCategories() {
        val retrySubject = PublishSubject.create<Any>()
        mvpView.showLoading()
        compositeDisposable.addAll(
                dataManager.mockCategories()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .doOnError { throwable ->
                            Timber.e(throwable)
                            retrySubject.onNext(Any())
                        }
                        .retryWhen { observable: Observable<Throwable> ->
                            observable.zipWith(retrySubject) { o: Throwable?, _: Any? -> o }
                        }
                        .subscribe { it ->
                            mvpView.updateDeals(it)
                            mvpView.hideLoading()
                        }
        )
    }
}