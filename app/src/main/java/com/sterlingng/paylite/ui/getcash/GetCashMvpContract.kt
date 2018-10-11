package com.sterlingng.paylite.ui.getcash

import com.sterlingng.paylite.ui.base.MvpPresenter

interface GetCashMvpContract<V : GetCashMvpView> : MvpPresenter<V> {
    fun cashOutViaPayCode(data: HashMap<String, Any>, self: Boolean)
}