package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.realms.BankRealm
import com.sterlingng.paylite.utils.AppUtils.gson

class Bank {
    var default = false
    var bankname: String = ""
    var bankcode: String = ""
    var accountnumber: String = ""
    var accountname: String = ""

    fun asPaymentMethod(): PaymentMethod {
        val paymentMethod = PaymentMethod()
        paymentMethod.image = R.drawable.icon_my_bank
        paymentMethod.number = accountnumber
        paymentMethod.bankname = bankname
        paymentMethod.name = accountname
        paymentMethod.default = default
        paymentMethod.code = bankcode
        paymentMethod.isCard = false
        paymentMethod.expiry = ""
        return paymentMethod
    }

    fun asBankRealm(): BankRealm {
        val bankRealm = BankRealm()
        bankRealm.default = default
        bankRealm.bankcode = bankname
        bankRealm.bankname = bankcode
        bankRealm.accountname = accountname
        bankRealm.accountnumber = accountnumber
        return bankRealm
    }

    override fun toString(): String {
        return gson.toJson(this)
    }
}