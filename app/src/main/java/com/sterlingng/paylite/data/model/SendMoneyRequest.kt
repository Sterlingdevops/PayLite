package com.sterlingng.paylite.data.model

import android.os.Parcel
import android.os.Parcelable

class SendMoneyRequest() : Parcelable {
    var amount = ""
    var recipientHandle: String = ""
    var user: String = ""
    var recipientName: String = ""
    var paymentReference: String = ""
    var repeatType = "-1"

    constructor(parcel: Parcel) : this() {
        amount = parcel.readString()
        recipientHandle = parcel.readString()
        user = parcel.readString()
        recipientName = parcel.readString()
        paymentReference = parcel.readString()
        repeatType = parcel.readString()
    }

    fun toHashMap(): HashMap<String, Any> {
        val data = HashMap<String, Any>()
        data["Bvn"] = user
        data["Amount"] = amount
        data["RepeatType"] = repeatType
        data["ReceipientName"] = recipientName
        data["PaymentReference"] = paymentReference
        data["ReceipientHandle"] = recipientHandle.toLowerCase()
        return data
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(amount)
        parcel.writeString(recipientHandle)
        parcel.writeString(user)
        parcel.writeString(recipientName)
        parcel.writeString(paymentReference)
        parcel.writeString(repeatType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SendMoneyRequest> {
        override fun createFromParcel(parcel: Parcel): SendMoneyRequest {
            return SendMoneyRequest(parcel)
        }

        override fun newArray(size: Int): Array<SendMoneyRequest?> {
            return arrayOfNulls(size)
        }
    }

}