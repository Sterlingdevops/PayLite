package com.sterlingng.paylite.ui.send

import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.MvpView

interface SendMoneyMvpView : MvpView {
    fun updateContacts(it: ArrayList<PayliteContact>)
    fun initView(wallet: Wallet?)
}