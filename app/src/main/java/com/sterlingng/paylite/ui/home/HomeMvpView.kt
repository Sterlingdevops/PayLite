package com.sterlingng.paylite.ui.home

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.MvpView

interface HomeMvpView : MvpView {
    fun onGetWalletFailed(it: Throwable)
    fun onGetWalletSuccessful(it: Response)
}
