package com.sterlingng.paylite.data.model

import android.os.Parcel
import android.os.Parcelable

class SendMoneyRequest() : Parcelable {
    var amount = 0
    var rcpt: String = ""
    var user: String = ""
    var channel: String = ""
    var comments: String = ""

    constructor(parcel: Parcel) : this() {
        amount = parcel.readInt()
        rcpt = parcel.readString()
        user = parcel.readString()
        channel = parcel.readString()
        comments = parcel.readString()
    }

    fun toHashMap(): HashMap<String, Any> {
        val data = HashMap<String, Any>()
        data["user"] = user
        data["rcpt"] = rcpt
        data["amount"] = amount
        data["channel"] = channel
        data["comments"] = comments
        return data
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(amount)
        parcel.writeString(rcpt)
        parcel.writeString(user)
        parcel.writeString(channel)
        parcel.writeString(comments)
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