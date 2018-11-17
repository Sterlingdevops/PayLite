package com.sterlingng.paylite.ui.base

/**
 * Created by rtukpe on 13/03/2018.
 */

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
open class BasePresenter<V : MvpView> @Inject
constructor(protected val dataManager: DataManager,
            val schedulerProvider: SchedulerProvider,
            protected val compositeDisposable: CompositeDisposable) : MvpPresenter<V> {
    protected lateinit var mvpView: V

    override fun  onViewInitialized() {

    }

    override fun onAttach(mvpView: V) {
        this.mvpView = mvpView
    }

    override fun onDetach() {
        compositeDisposable.dispose()
    }

    companion object {

        private val TAG = "BasePresenter"
    }
}