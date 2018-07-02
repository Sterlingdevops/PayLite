package com.sterlingng.paylite.ui.home

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class HomePresenter<V : HomeMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), HomeMvpContract<V> {

    override fun loadDeals()  {
        val retrySubject = PublishSubject.create<Any>()
        mvpView.showLoading()
        compositeDisposable.addAll(
                dataManager.mockDeals()
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