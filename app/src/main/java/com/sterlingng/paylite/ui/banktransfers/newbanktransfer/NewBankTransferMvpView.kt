package com.sterlingng.paylite.ui.banktransfers.newbanktransfer

import com.sterlingng.paylite.data.model.BankNameEnquiry
import com.sterlingng.paylite.ui.base.MvpView

interface NewBankTransferMvpView : MvpView {
    fun onResolveBankAccountFailed()
    fun onResolveBankAccountSuccessful(bankNameEnquiry: BankNameEnquiry)
    fun logout()
}