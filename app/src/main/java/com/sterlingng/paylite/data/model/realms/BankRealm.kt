package com.sterlingng.paylite.data.model.realms

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class BankRealm : RealmObject() {
    lateinit var bankcode: String
    lateinit var bankname: String
    lateinit var accountname: String
    @PrimaryKey
    lateinit var accountnumber: String
}