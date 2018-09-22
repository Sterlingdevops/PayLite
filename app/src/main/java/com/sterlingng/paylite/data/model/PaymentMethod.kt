package com.sterlingng.paylite.data.model

import android.os.Parcel
import android.os.Parcelable
import com.sterlingng.paylite.utils.AppUtils.gson

class PaymentMethod() : Parcelable {
    var number: String = ""
    var expiry: String = ""
    var code: String = ""
    var bankname: String = ""
    var name: String = ""

    var image: Int = 0

    var default: Boolean = false
    var isCard: Boolean = false

    constructor(parcel: Parcel) : this() {
        number = parcel.readString()
        name = parcel.readString()
        expiry = parcel.readString()
        image = parcel.readInt()
        isCard = parcel.readByte() != 0.toByte()
    }

    override fun toString(): String {
        return gson.toJson(this)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(number)
        parcel.writeString(name)
        parcel.writeString(expiry)
        parcel.writeInt(image)
        parcel.writeByte(if (isCard) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun asBank(default: Boolean): Bank {
        val bank = Bank()
        bank.accountnumber = number
        bank.bankname = bankname
        bank.accountname = name
        bank.default = default
        bank.bankcode = code
        return bank
    }

    fun asCard(default: Boolean): Card {
        val card = Card()
        card.default = default
        card.expiry = expiry
        card.number = number
        card.name = name
        return card
    }


    companion object CREATOR : Parcelable.Creator<PaymentMethod> {
        override fun createFromParcel(parcel: Parcel): PaymentMethod {
            return PaymentMethod(parcel)
        }

        override fun newArray(size: Int): Array<PaymentMethod?> {
            return arrayOfNulls(size)
        }
    }

}