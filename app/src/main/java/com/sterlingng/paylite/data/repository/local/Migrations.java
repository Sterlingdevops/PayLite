package com.sterlingng.paylite.data.repository.local;

import android.support.annotation.NonNull;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class Migrations implements RealmMigration {

    @Override
    public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        //version 2
        if (oldVersion == 1) {
            schema.get("UserRealm")
                    .addField("token", String.class)
                    .setRequired("token", true)
            ;
            ++oldVersion;
        }

        //version 3
        if (oldVersion == 2) {
            schema.create("WalletRealm")
                    .addField("balance", Integer.class)
                    .addField("name", String.class)
                    .addField("walletid", String.class)
                    .setRequired("balance", true)
                    .setRequired("name", true)
                    .setRequired("walletid", true)
                    .addPrimaryKey("walletid")
            ;
            ++oldVersion;
        }

        //version 4
        if (oldVersion == 3) {
            schema.get("UserRealm")
                    .removeField("token")
                    .removeField("userid")
                    .removeField("wallet")
                    .addPrimaryKey("bvn")
            ;
            ++oldVersion;
        }

        //version 5
        if (oldVersion == 4) {
            schema.get("UserRealm")
                    .removeField("bvn")
                    .removeField("latitude")
                    .removeField("longitude")
                    .removeField("username")
                    .addField("accesstoken", String.class)
                    .setRequired("accesstoken", true)
                    .addPrimaryKey("phone")
            ;
            ++oldVersion;
        }

        //version 6
        if (oldVersion == 5) {
            schema.create("PinRealm")
                    .addField("pin", String.class)
                    .addField("phone", String.class)
                    .setRequired("pin", true)
                    .setRequired("phone", true)
                    .addPrimaryKey("phone")
            ;
            ++oldVersion;
        }

        //version 7
        if (oldVersion == 6) {
            schema.create("TransactionRealm")
                    .addField("id", String.class)
                    .addField("name", String.class)
                    .addField("type", String.class)
                    .addField("date", String.class)
                    .addField("amount", String.class)
                    .addField("reference", String.class)
                    .setRequired("id", true)
                    .setRequired("name", true)
                    .setRequired("type", true)
                    .setRequired("date", true)
                    .setRequired("amount", true)
                    .setRequired("reference", true)
                    .addPrimaryKey("id")
            ;
            ++oldVersion;
        }

        //version 8
        if (oldVersion == 7) {
            schema.create("BankRealm")
                    .addField("bankcode", String.class)
                    .addField("bankname", String.class)
                    .addField("accountname", String.class)
                    .addField("accountnumber", String.class)
                    .setRequired("bankcode", true)
                    .setRequired("bankname", true)
                    .setRequired("accountname", true)
                    .setRequired("accountnumber", true)
                    .addPrimaryKey("accountnumber")
            ;

            schema.create("CardRealm")
                    .addField("name", String.class)
                    .addField("number", String.class)
                    .addField("expiry", String.class)
                    .setRequired("name", true)
                    .setRequired("number", true)
                    .setRequired("expiry", true)
                    .addPrimaryKey("number")
            ;
            ++oldVersion;
        }
    }
}
