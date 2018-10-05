package com.sterlingng.paylite.data.model.realms

import com.sterlingng.paylite.data.model.ScheduledPayment
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ScheduledRealm : RealmObject() {
    @PrimaryKey
    var reference: Int = 0
    var amount: Int = 0
    var interval: Int = 0
    var numberoftimespaidout: Int = 0

    lateinit var datelastpaid: String
    lateinit var userid: String
    lateinit var narration: String
    lateinit var paymentref: String
    lateinit var beneficiaryid: String
    lateinit var startdate: String
    lateinit var enddate: String
    lateinit var status: String
    lateinit var dateadded: String
    lateinit var active: String

    fun asScheduledPayment(): ScheduledPayment {
        val scheduledPayment = ScheduledPayment()
        scheduledPayment.userId = userid
        scheduledPayment.status = status
        scheduledPayment.active = active
        scheduledPayment.amount = amount
        scheduledPayment.endDate = enddate
        scheduledPayment.interval = interval
        scheduledPayment.dateAdded = dateadded
        scheduledPayment.narration = narration
        scheduledPayment.reference = reference
        scheduledPayment.startDate = startdate
        scheduledPayment.paymentRef = paymentref
        scheduledPayment.datelastPaid = datelastpaid
        scheduledPayment.beneficiaryId = beneficiaryid
        scheduledPayment.numberOfTimesPaidOut = numberoftimespaidout
        return scheduledPayment
    }
}