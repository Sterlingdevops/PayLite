package com.sterlingng.paylite.data.repository.local.interfaces

import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.data.model.realms.UserRealm

/**
 * Created by rtukpe on 22/03/2018.
 */

interface LocalDataInterface {

    //logout

    fun deleteAll()
    fun closeRealm()

    //users

    fun deleteAllUsers()
    fun saveUser(user: User)
    fun getCurrentUser(): User?
    fun getUserRealm(): UserRealm?
}
