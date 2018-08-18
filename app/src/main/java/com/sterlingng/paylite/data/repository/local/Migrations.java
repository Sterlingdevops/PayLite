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
                    .setRequired("token", true);
            ++oldVersion;
        }
    }
}
