package com.sterlingng.paylite.data.model.realms

import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.utils.AppUtils
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ContactRealm : RealmObject() {
    @PrimaryKey
    lateinit var id: String
    lateinit var name: String
    lateinit var phone: String
    lateinit var email: String
    lateinit var accountname: String
    lateinit var accountnumber: String

    fun asPayliteContact(): PayliteContact {
        val contact = PayliteContact()
        contact.accountnumber = accountnumber
        contact.accountname = accountname
        contact.phone = phone
        contact.email = email
        contact.name = name
        contact.id = id
        return contact
    }

    override fun toString(): String {
        return AppUtils.gson.toJson(this)
    }
}