package com.sterlingng.paylite.ui.banktransfers.banktransferamount

import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.ui.base.MvpPresenter

interface BankTransferAmountMvpContract<V : BankTransferAmountMvpView> : MvpPresenter<V> {
    fun loadCachedWallet()
    fun bankTransfer(data: HashMap<String, Any>)
    fun bankTransferSterling(data: HashMap<String, Any>)
    fun saveContact(contact: PayliteContact)
    fun schedulePayment(data: java.util.HashMap<String, Any>)
}
