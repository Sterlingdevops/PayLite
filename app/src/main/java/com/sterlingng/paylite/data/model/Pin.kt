package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.data.model.realms.PinRealm
import com.sterlingng.paylite.utils.PIN_KEY
import com.sterlingng.paylite.utils.encryptAES

class Pin {
    var pin = ""
    var phone = ""

    fun asPinRealm(): PinRealm {
        val pinRealm = PinRealm()
        pinRealm.pin = pin.encryptAES(PIN_KEY)
        pinRealm.phone = phone
        return pinRealm
    }
}