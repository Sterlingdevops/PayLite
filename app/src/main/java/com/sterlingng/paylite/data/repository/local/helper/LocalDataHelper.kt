package com.sterlingng.paylite.data.repository.local.helper

import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.data.model.realms.UserRealm
import com.sterlingng.paylite.data.model.realms.WalletRealm
import com.sterlingng.paylite.data.repository.local.Migrations
import com.sterlingng.paylite.data.repository.local.interfaces.LocalDataInterface
import com.sterlingng.paylite.utils.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

/**
 * Created by rtukpe on 22/03/2018.
 */

class LocalDataHelper @Inject
constructor() : LocalDataInterface {

    private val config: RealmConfiguration = RealmConfiguration.Builder().schemaVersion(3).migration(Migrations()).build()
    private val realm: Realm

    init {
        realm = Realm.getInstance(config)
    }

    private fun getRealm(): Realm {
        if (realm.isClosed)
            Realm.getInstance(config)
        return realm
    }

    override fun closeRealm() {
        getRealm().close()
    }

    override fun getCurrentUser(): User? {
        return getUserRealm()?.asUser()
    }

    override fun deleteAllUsers() {
        getRealm().beginTransaction()
        try {
            getRealm().delete(UserRealm::class.java)
        } catch (e: IllegalStateException) {
            Log.e(e, "LocalDataHelper->deleteAllUsers")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun deleteAll() {
        getRealm().beginTransaction()
        try {
            getRealm().deleteAll()
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->deleteAll")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun getUserRealm(): UserRealm? {
        return getRealm().where(UserRealm::class.java).findFirst()
    }

    override fun saveUser(user: User) {
        getRealm().beginTransaction()
        try {
            getRealm().copyToRealmOrUpdate(user.asUserRealm())
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->saveUser")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun deleteAllWallets() {
        getRealm().beginTransaction()
        try {
            getRealm().delete(WalletRealm::class.java)
        } catch (e: IllegalStateException) {
            Log.e(e, "LocalDataHelper->deleteAllWallets")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun getWallet(): Wallet? {
        return getWalletRealm()?.asWallet()
    }

    override fun saveWallet(wallet: Wallet) {
        getRealm().beginTransaction()
        try {
            getRealm().copyToRealmOrUpdate(wallet.asWalletRealm())
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->saveWallet")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun getWalletRealm(): WalletRealm? {
        return getRealm().where(WalletRealm::class.java).findFirst()
    }
}