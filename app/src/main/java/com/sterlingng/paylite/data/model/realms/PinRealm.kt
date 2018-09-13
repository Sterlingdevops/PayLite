package com.sterlingng.paylite.data.model.realms

import com.sterlingng.paylite.data.model.Pin
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PinRealm : RealmObject() {
    @PrimaryKey
    lateinit var phone: String
    lateinit var pin: String

    fun asPin(): Pin {
        val pin1 = Pin()
        pin1.pin = pin
        pin1.phone = phone
        return pin1
    }
}