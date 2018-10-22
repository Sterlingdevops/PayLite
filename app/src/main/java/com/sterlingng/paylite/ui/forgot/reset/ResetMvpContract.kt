package com.sterlingng.paylite.ui.forgot.reset

import com.sterlingng.paylite.ui.base.MvpPresenter

interface ResetMvpContract<V : ResetMvpView> : MvpPresenter<V> {
    fun resetPassword(data: HashMap<String, Any>, type: Int)
}