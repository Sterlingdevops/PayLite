package com.sterlingng.paylite.ui.newpaymentamount

import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.ui.base.MvpPresenter
import java.util.*

interface NewPaymentAmountMvpContract<V : NewPaymentAmountMvpView> : MvpPresenter<V> {
    fun loadCachedWallet()
    fun saveContact(contact: PayliteContact)
    fun sendMoney(data: HashMap<String, Any>)
}