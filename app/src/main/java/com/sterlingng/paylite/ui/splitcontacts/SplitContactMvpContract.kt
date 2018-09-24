package com.sterlingng.paylite.ui.splitcontacts

import com.sterlingng.paylite.ui.base.MvpPresenter

interface SplitContactMvpContract<V : SplitContactMvpView> : MvpPresenter<V> {
    fun splitPayment(data: HashMap<String, Any>)
}