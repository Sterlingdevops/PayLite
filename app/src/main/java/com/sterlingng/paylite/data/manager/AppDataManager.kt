package com.sterlingng.paylite.data.manager

import android.content.Context
import com.sterlingng.paylite.data.model.*
import com.sterlingng.paylite.data.repository.mock.MockHelper
import com.sterlingng.paylite.data.repository.remote.helpers.RemoteServiceHelper
import com.sterlingng.paylite.di.annotations.ApplicationContext
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rtukpe on 14/03/2018.
 */

@Singleton
class AppDataManager
@Inject
internal constructor(@param:ApplicationContext val context: Context,
                     private val remoteServiceHelper: RemoteServiceHelper,
                     private val mockHelper: MockHelper) : DataManager {

    override fun mockContacts(): ArrayList<Contact> = mockHelper.mockContacts()

    override fun mockPaymentMethods(): ArrayList<PaymentMethod> = mockHelper.mockPaymentMethods()

    override fun mockCategories(): ArrayList<PaymentCategory> = mockHelper.mockCategories()

    override fun mockLogin(email: String, password: String): Observable<User> = mockHelper.mockLogin(email, password)

    override fun mockTransactions(): ArrayList<Transaction> = mockHelper.mockTransactions()

    override fun mockNotifications(): ArrayList<Notification> = mockHelper.mockNotifications()

    override fun mockCharities(): ArrayList<Charity> = mockHelper.mockCharities()

    override fun mockProjects(): ArrayList<Project> = mockHelper.mockProjects()

    override fun mockDeals(): ArrayList<Deal> = mockHelper.mockDeals()
}