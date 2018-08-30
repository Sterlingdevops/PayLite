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
    }
}
