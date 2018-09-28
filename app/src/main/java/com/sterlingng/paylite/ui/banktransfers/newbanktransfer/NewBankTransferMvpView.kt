package com.sterlingng.paylite.ui.banktransfers.newbanktransfer

import com.sterlingng.paylite.data.model.CashOutToBankAccountRequest
import com.sterlingng.paylite.ui.base.MvpView

interface NewBankTransferMvpView : MvpView {
    fun onResolveBankAccountFailed()
    fun onResolveBankAccountSuccessful(cashOutToBankAccountRequest: CashOutToBankAccountRequest)
    fun logout()
}