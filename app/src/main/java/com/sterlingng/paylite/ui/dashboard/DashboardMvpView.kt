package com.sterlingng.paylite.ui.dashboard

import com.sterlingng.paylite.data.model.Bank
import com.sterlingng.paylite.ui.base.MvpView

/**
 * Created by rtukpe on 21/03/2018.
 */

interface DashboardMvpView : MvpView {
    fun logout()
    fun onGetBanksSuccessful(banks: ArrayList<Bank>)
    fun onGetBanksFailed()
}
