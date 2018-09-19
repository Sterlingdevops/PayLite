package com.sterlingng.paylite.data.model.realms

import com.sterlingng.paylite.data.model.Bank
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class BankRealm : RealmObject() {
    @PrimaryKey
    lateinit var accountnumber: String
    lateinit var bankcode: String
    lateinit var bankname: String
    lateinit var accountname: String
    var default = false

    fun asBank(): Bank {
        val bank = Bank()
        bank.default = default
        bank.bankcode = bankname
        bank.bankname = bankcode
        bank.accountname = accountname
        bank.accountnumber = accountnumber
        return bank
    }
}