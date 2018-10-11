package com.sterlingng.paylite.ui.banktransfers.contacts

import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.ui.base.MvpView

interface ContactsMvpView : MvpView {
    fun initView(contacts: ArrayList<PayliteContact>)
}