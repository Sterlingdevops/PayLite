package com.sterlingng.paylite.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.utils.AppUtils.gson

class CashOutToBankAccountRequest() : Parcelable {
    @SerializedName("SessionID")
    var sessionId: String = ""

    @SerializedName("ToAccount")
    var toAccount: String = ""

    @SerializedName("PaymentReference")
    var paymentReference: String = ""

    @SerializedName("PhoneNumber")
    var phoneNumber: String = ""

    @SerializedName("Amount")
    var amount: Int = 0

    @SerializedName("BeneficiaryName")
    var beneficiaryName: String = ""

    @SerializedName("DestionationBankCode")
    var destinationBankCode: String = ""

    constructor(parcel: Parcel) : this() {
        sessionId = parcel.readString()
        toAccount = parcel.readString()
        paymentReference = parcel.readString()
        phoneNumber = parcel.readString()
        amount = parcel.readInt()
        beneficiaryName = parcel.readString()
        destinationBankCode = parcel.readString()
    }


    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["Amount"] = amount
        hashMap["SessionID"] = sessionId
        hashMap["ToAccount"] = toAccount
        hashMap["PhoneNumber"] = phoneNumber
        hashMap["BeneficiaryName"] = beneficiaryName
        hashMap["PaymentReference"] = paymentReference
        hashMap["DestionationBankCode"] = destinationBankCode
        return hashMap
    }

    override fun toString(): String {
        return gson.toJson(this)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sessionId)
        parcel.writeString(toAccount)
        parcel.writeString(paymentReference)
        parcel.writeString(phoneNumber)
        parcel.writeInt(amount)
        parcel.writeString(beneficiaryName)
        parcel.writeString(destinationBankCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CashOutToBankAccountRequest> {
        override fun createFromParcel(parcel: Parcel): CashOutToBankAccountRequest {
            return CashOutToBankAccountRequest(parcel)
        }

        override fun newArray(size: Int): Array<CashOutToBankAccountRequest?> {
            return arrayOfNulls(size)
        }
    }

}