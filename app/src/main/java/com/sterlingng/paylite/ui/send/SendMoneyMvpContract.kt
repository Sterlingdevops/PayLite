package com.sterlingng.paylite.ui.send

import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.ui.base.MvpPresenter

interface SendMoneyMvpContract<V : SendMoneyMvpView> : MvpPresenter<V> {
    fun loadContacts()
    fun loadCachedWallet()
    fun deleteContact(contact: PayliteContact)
}