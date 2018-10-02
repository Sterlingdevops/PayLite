package com.sterlingng.paylite.ui.authpin

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Pin
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.Log
import com.sterlingng.paylite.utils.PIN_KEY
import com.sterlingng.paylite.utils.encryptAES
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AuthPinPresenter<V : AuthPinMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), AuthPinMvpContract<V> {

    override fun validate(pinText: String): Boolean {
        return dataManager.getPin(dataManager.getCurrentUser()?.phoneNumber!!)?.pin == pinText.encryptAES(PIN_KEY)
    }

    override fun savePin(pinText: String) {
        val pin = Pin()
        pin.pin = pinText
        pin.phone = dataManager.getCurrentUser()?.phoneNumber!!
        dataManager.savePin(pin)
    }
}