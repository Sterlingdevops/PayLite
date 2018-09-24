package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.data.model.realms.ContactRealm
import com.sterlingng.paylite.utils.AppUtils.gson

class PayliteContact {
    var id: String = ""
    var name: String = ""
    var phone: String = ""
    var email: String = ""
    var image: Int = 0

    constructor(name: String, image: Int) {
        this.name = name
        this.image = image
    }

    constructor()

    fun toContactRealm(): ContactRealm {
        val contactRealm = ContactRealm()
        contactRealm.email = email
        contactRealm.phone = phone
        contactRealm.name = name
        contactRealm.id = id
        return contactRealm
    }

    override fun toString(): String {
        return gson.toJson(this)
    }
}