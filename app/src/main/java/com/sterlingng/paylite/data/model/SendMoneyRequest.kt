package com.sterlingng.paylite.data.model

import android.os.Parcel
import android.os.Parcelable

class SendMoneyRequest() : Parcelable {
    var email = ""
    var phone = ""
    var amount = ""
    var repeatType = "-1"
    var recipientName: String = ""
    var paymentReference: String = ""

    constructor(parcel: Parcel) : this() {
        email = parcel.readString()
        phone = parcel.readString()
        amount = parcel.readString()
        recipientName = parcel.readString()
        paymentReference = parcel.readString()
        repeatType = parcel.readString()
    }

    fun toHashMap(): HashMap<String, Any> {
        val data = HashMap<String, Any>()
        data["Amount"] = amount
        data["RecipientEmail"] = email
        data["RepeatType"] = repeatType
        data["RecipientPhoneNumber"] = phone
        data["ReceipientName"] = recipientName
        data["PaymentReference"] = paymentReference
        return data
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(amount)
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