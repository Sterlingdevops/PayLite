package com.sterlingng.paylite.ui.confirm

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.Log
import com.sterlingng.paylite.utils.PIN_KEY
import com.sterlingng.paylite.utils.encryptAES
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by rtukpe on 21/03/2018.
 */

class ConfirmPresenter<V : ConfirmMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), ConfirmMvpContract<V> {

    override fun validatePin(value: String) {
        Log.d(dataManager.getCurrentUser()?.toString()!!)
        Log.d(dataManager.getPin(dataManager.getCurrentUser()?.phoneNumber!!).toString())

        if (dataManager.getPin(dataManager.getCurrentUser()?.phoneNumber!!)?.pin == value.encryptAES(PIN_KEY))
            mvpView.onPinEnteredCorrect()
        else mvpView.onPinEnteredIncorrect()
    }
}
