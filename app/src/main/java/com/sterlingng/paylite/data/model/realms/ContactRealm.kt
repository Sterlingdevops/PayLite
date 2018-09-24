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

    fun toPayliteContact(): PayliteContact {
        val payliteContact = PayliteContact()
        payliteContact.email = email
        payliteContact.phone = phone
        payliteContact.name = name
        payliteContact.id = id
        return payliteContact
    }

    override fun toString(): String {
        return AppUtils.gson.toJson(this)
    }
}