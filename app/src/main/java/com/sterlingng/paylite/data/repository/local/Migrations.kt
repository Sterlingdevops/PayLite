package com.sterlingng.paylite.data.repository.local

import io.realm.DynamicRealm
import io.realm.RealmMigration

class Migrations : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var version = oldVersion

        val schema = realm.schema

        //version 2
        if (version == 1L) {
            schema.get("UserRealm")!!
                    .addField("token", String::class.java)
                    .setRequired("token", true)
            ++version
        }

        //version 3
        if (version == 2L) {
            schema.create("WalletRealm")
                    .addField("balance", Int::class.java)
                    .addField("name", String::class.java)
                    .addField("walletid", String::class.java)
                    .setRequired("balance", true)
                    .setRequired("name", true)
                    .setRequired("walletid", true)
                    .addPrimaryKey("walletid")
            ++version
        }

        //version 4
        if (version == 3L) {
            schema.get("UserRealm")!!
                    .removeField("token")
                    .removeField("userid")
                    .removeField("wallet")
                    .addPrimaryKey("bvn")
            ++version
        }

        //version 5
        if (version == 4L) {
            schema.get("UserRealm")!!
                    .removeField("bvn")
                    .removeField("latitude")
                    .removeField("longitude")
                    .removeField("username")
                    .addField("accesstoken", String::class.java)
                    .setRequired("accesstoken", true)
                    .addPrimaryKey("phone")
            ++version
        }

        //version 6
        if (version == 5L) {
            schema.create("PinRealm")
                    .addField("pin", String::class.java)
                    .addField("phone", String::class.java)
                    .setRequired("pin", true)
                    .setRequired("phone", true)
                    .addPrimaryKey("phone")
            ++version
        }

        //version 7
        if (version == 6L) {
            schema.create("TransactionRealm")
                    .addField("id", String::class.java)
                    .addField("name", String::class.java)
                    .addField("type", String::class.java)
                    .addField("date", String::class.java)
                    .addField("amount", String::class.java)
                    .addField("reference", String::class.java)
                    .setRequired("id", true)
                    .setRequired("name", true)
                    .setRequired("type", true)
                    .setRequired("date", true)
                    .setRequired("amount", true)
                    .setRequired("reference", true)
                    .addPrimaryKey("id")
            ++version
        }

        //version 8
        if (version == 7L) {
            schema.create("BankRealm")
                    .addField("bankcode", String::class.java)
                    .addField("bankname", String::class.java)
                    .addField("accountname", String::class.java)
                    .addField("accountnumber", String::class.java)
                    .addField("default", Boolean::class.javaPrimitiveType)
                    .setRequired("bankcode", true)
                    .setRequired("bankname", true)
                    .setRequired("accountname", true)
                    .setRequired("accountnumber", true)
                    .addPrimaryKey("accountnumber")

            schema.create("CardRealm")
                    .addField("name", String::class.java)
                    .addField("number", String::class.java)
                    .addField("expiry", String::class.java)
                    .addField("default", Boolean::class.javaPrimitiveType)
                    .setRequired("name", true)
                    .setRequired("number", true)
                    .setRequired("expiry", true)
                    .addPrimaryKey("number")
            ++version
        }

        //version 9
        if (version == 8L) {
            schema.get("BankRealm")!!
                    .removeField("default")

            schema.get("CardRealm")!!
                    .removeField("default")
            ++version
        }

        //version 10
        if (version == 9L) {
            schema.get("TransactionRealm")!!
                    .removeField("name")
                    .addField("mobile", String::class.java)
                    .addField("sendername", String::class.java)
                    .addField("recipientname", String::class.java)
                    .addField("recipientemail", String::class.java)
                    .addField("recipientphone", String::class.java)
                    .setRequired("mobile", true)
                    .setRequired("sendername", true)
                    .setRequired("recipientname", true)
                    .setRequired("recipientemail", true)
                    .setRequired("recipientphone", true)
            ++version
        }

        //version 11
        if (version == 10L) {
            schema.get("TransactionRealm")!!
                    .addField("credit", String::class.java)
                    .setRequired("credit", true)
            ++version
        }

        //version 12
        if (version == 11L) {
            schema.create("ContactRealm")
                    .addField("id", String::class.java)
                    .addField("name", String::class.java)
                    .addField("email", String::class.java)
                    .addField("phone", String::class.java)
                    .setRequired("id", true)
                    .setRequired("name", true)
                    .setRequired("email", true)
                    .setRequired("phone", true)
                    .addPrimaryKey("id")
            ++version
        }

        //version 13
        if (version == 12L) {
            schema.create("ScheduledRealm")
                    .addField("amount", Int::class.javaPrimitiveType)
                    .addField("interval", Int::class.javaPrimitiveType)
                    .addField("reference", Int::class.javaPrimitiveType)
                    .addField("numberoftimespaidout", Int::class.javaPrimitiveType)
                    .addField("active", String::class.java).setRequired("active", true)
                    .addField("status", String::class.java).setRequired("status", true)
                    .addField("userid", String::class.java).setRequired("userid", true)
                    .addField("enddate", String::class.java).setRequired("enddate", true)
                    .addField("narration", String::class.java).setRequired("narration", true)
                    .addField("startdate", String::class.java).setRequired("startdate", true)
                    .addField("dateadded", String::class.java).setRequired("dateadded", true)
                    .addField("paymentref", String::class.java).setRequired("paymentref", true)
                    .addField("datelastpaid", String::class.java).setRequired("datelastpaid", true)
                    .addField("beneficiaryid", String::class.java).setRequired("beneficiaryid", true)
                    .addPrimaryKey("reference")
            ++version
        }

        // version 14
        if (version == 13L) {
            schema.get("ContactRealm")!!
                    .addField("accountname", String::class.java)
                    .addField("accountnumber", String::class.java)
                    .setRequired("accountname", true)
                    .setRequired("accountnumber", true)
            ++version
        }

        // version 15
        if (version == 14L) {
            schema.get("TransactionRealm")!!
                    .addField("sendernumber", String::class.java)
                    .setRequired("sendernumber", true)
            ++version
        }

        // version 15
        if (version == 15L) {
            schema.get("TransactionRealm")!!
                    .addField("category", String::class.java)
                    .setRequired("category", true)
            ++version
        }
    }
}
