package com.sterlingng.paylite.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.data.model.realms.ScheduledRealm
import com.sterlingng.paylite.utils.AppUtils.gson

class ScheduledPayment() : Parcelable {
    @SerializedName("refid")
    var reference: Int = 0

    @SerializedName("DatelastPaid")
    var datelastPaid: String = ""

    @SerializedName("UserID")
    var userId: String = ""

    @SerializedName("numberOfTimesPaidOut")
    var numberOfTimesPaidOut: Int = 0

    @SerializedName("narration")
    var narration: String = ""

    @SerializedName("amount")
    var amount: Int = 0

    @SerializedName("payment_ref")
    var paymentRef: String = ""

    @SerializedName("beneficiaryID")
    var beneficiaryId: String = ""

    @SerializedName("start_date")
    var startDate: String = ""

    @SerializedName("end_date")
    var endDate: String = ""

    @SerializedName("status")
    var status: String = ""

    @SerializedName("interval")
    var interval: Int = 0

    @SerializedName("dateAdded")
    var dateAdded: String = ""

    @SerializedName("ActiveStatus")
    var active: String = ""

    fun asScheduledRealm(): ScheduledRealm {
        val scheduledRealm = ScheduledRealm()
        scheduledRealm.userid = userId
        scheduledRealm.status = status
        scheduledRealm.active = active
        scheduledRealm.amount = amount
        scheduledRealm.enddate = endDate
        scheduledRealm.interval = interval
        scheduledRealm.dateadded = dateAdded
        scheduledRealm.narration = narration
        scheduledRealm.reference = reference
        scheduledRealm.startdate = startDate
        scheduledRealm.paymentref = paymentRef
        scheduledRealm.datelastpaid = datelastPaid
        scheduledRealm.beneficiaryid = beneficiaryId
        scheduledRealm.numberoftimespaidout = numberOfTimesPaidOut
        return scheduledRealm
    }

    constructor(parcel: Parcel) : this() {
        reference = parcel.readInt()
        datelastPaid = parcel.readString()
        userId = parcel.readString()
        numberOfTimesPaidOut = parcel.readInt()
        narration = parcel.readString()
        amount = parcel.readInt()
        paymentRef = parcel.readString()
        beneficiaryId = parcel.readString()
        startDate = parcel.readString()
        endDate = parcel.readString()
        status = parcel.readString()
        interval = parcel.readInt()
        dateAdded = parcel.readString()
        active = parcel.readString()
    }

    override fun toString(): String {
        return gson.toJson(this)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(reference)
        parcel.writeString(datelastPaid)
        parcel.writeString(userId)
        parcel.writeInt(numberOfTimesPaidOut)
        parcel.writeString(narration)
        parcel.writeInt(amount)
        parcel.writeString(paymentRef)
        parcel.writeString(beneficiaryId)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
        parcel.writeString(status)
        parcel.writeInt(interval)
        parcel.writeString(dateAdded)
        parcel.writeString(active)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScheduledPayment> {
        override fun createFromParcel(parcel: Parcel): ScheduledPayment {
            return ScheduledPayment(parcel)
        }

        override fun newArray(size: Int): Array<ScheduledPayment?> {
            return arrayOfNulls(size)
        }
    }
}