package com.sterlingng.paylite.ui.securityquestions

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SetSecurityQuestionMvpPresenter<V : SetSecurityQuestionMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SetSecurityQuestionMvpContract<V> {

    override fun loadQuestions() {
        mvpView.initView(dataManager.mockQuestions())
    }

    override fun saveQuestions(data: HashMap<String, Any>) {
        mvpView.showLoading()
        dataManager.getCurrentUser()?.phoneNumber?.let { data["UserAcct"] = it }
        dataManager
                .setSecurityQuestion(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", AppUtils.gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onSaveQuestionsSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onSaveQuestionsFailed()
                        mvpView.hideLoading()
                    }
                })
    }
}