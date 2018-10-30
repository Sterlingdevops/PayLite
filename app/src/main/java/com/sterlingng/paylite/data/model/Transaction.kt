package com.sterlingng.paylite.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.data.model.realms.TransactionRealm
import com.sterlingng.paylite.utils.AppUtils.gson

class Transaction() : Parcelable {

    @SerializedName("Refid")
    var id: Int = 0

    @SerializedName("Amount")
    var amount = ""

    var amountInt = 0

    @SerializedName("Mobile")
    var mobile: String = ""

    @SerializedName("Transtype")
    var type: Int = 0

    @SerializedName("DebitCreditFlag")
    var credit: String = ""

    @SerializedName("TransDate")
    var date: String = ""

    @SerializedName("SenderName")
    var senderName: String = ""

    @SerializedName("ReceiverName")
    var recipientName: String = ""

    @SerializedName("RecipientEmail")
    var recipientEmail: String = ""

    @SerializedName("PaymentReference")
    var reference: String = ""

    @SerializedName("RecipientPhoneNumber")
    var recipientPhoneNumber: String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        amount = parcel.readString()
        mobile = parcel.readString()
        type = parcel.readInt()
        credit = parcel.readString()
        date = parcel.readString()
        senderName = parcel.readString()
        recipientName = parcel.readString()
        recipientEmail = parcel.readString()
        reference = parcel.readString()
        recipientPhoneNumber = parcel.readString()
    }

    fun asTransactionRealm(): TransactionRealm {
        val transactionRealm = TransactionRealm()
        transactionRealm.recipientphone = recipientPhoneNumber
        transactionRealm.recipientemail = recipientEmail
        transactionRealm.recipientname = recipientName
        transactionRealm.sendername = senderName
        transactionRealm.type = type.toString()
        transactionRealm.reference = reference
        transactionRealm.id = id.toString()
        transactionRealm.amount = amount
        transactionRealm.credit = credit
        transactionRealm.mobile = mobile
        transactionRealm.date = date
        return transactionRealm
    }

    override fun toString(): String {
        return gson.toJson(this)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(amount)
        parcel.writeString(mobile)
        parcel.writeInt(type)
        parcel.writeString(credit)
        parcel.writeString(date)
        parcel.writeString(senderName)
        parcel.writeString(recipientName)
        parcel.writeString(recipientEmail)
        parcel.writeString(reference)
        parcel.writeString(recipientPhoneNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }
}