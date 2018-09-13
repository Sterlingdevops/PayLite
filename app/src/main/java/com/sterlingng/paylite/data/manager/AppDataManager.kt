package com.sterlingng.paylite.data.manager

import android.content.Context
import com.sterlingng.paylite.data.model.*
import com.sterlingng.paylite.data.model.realms.PinRealm
import com.sterlingng.paylite.data.model.realms.UserRealm
import com.sterlingng.paylite.data.model.realms.WalletRealm
import com.sterlingng.paylite.data.repository.local.helper.LocalDataHelper
import com.sterlingng.paylite.data.repository.mock.MockHelper
import com.sterlingng.paylite.data.repository.remote.helpers.PayStackServiceHelper
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
                     private val mLocalDataHelper: LocalDataHelper,
                     private val mRemoteServiceHelper: RemoteServiceHelper,
                     private val mPayStackServiceHelper: PayStackServiceHelper,
                     private val mMockHelper: MockHelper) : DataManager {

    override fun getPinRealm(): PinRealm? = mLocalDataHelper.getPinRealm()

    override fun getPin(): Pin? = mLocalDataHelper.getPin()

    override fun savePin(pin: Pin) = mLocalDataHelper.savePin(pin)

    override fun getWallet(): Wallet? = mLocalDataHelper.getWallet()

    override fun mockBanks(): ArrayList<Bank> = mMockHelper.mockBanks()

    override fun deleteAllWallets() = mLocalDataHelper.deleteAllWallets()

    override fun saveWallet(wallet: Wallet) = mLocalDataHelper.saveWallet(wallet)

    override fun getWalletRealm(): WalletRealm? = mLocalDataHelper.getWalletRealm()

    override fun getBanks(): Observable<Response> = mPayStackServiceHelper.getBanks()

    override fun deleteAll() = mLocalDataHelper.deleteAll()

    override fun closeRealm() = mLocalDataHelper.closeRealm()

    override fun deleteAllUsers() = mLocalDataHelper.deleteAllUsers()

    override fun mockDeals(): ArrayList<Deal> = mMockHelper.mockDeals()

    override fun saveUser(user: User) = mLocalDataHelper.saveUser(user)

    override fun getCurrentUser(): User? = mLocalDataHelper.getCurrentUser()

    override fun getUserRealm(): UserRealm? = mLocalDataHelper.getUserRealm()

    override fun mockContacts(): ArrayList<PayliteContact> = mMockHelper.mockContacts()

    override fun mockPaymentMethods(): ArrayList<PaymentMethod> = mMockHelper.mockPaymentMethods()

    override fun mockCategories(): ArrayList<PaymentCategory> = mMockHelper.mockCategories()

    override fun mockTransactions(): ArrayList<Transaction> = mMockHelper.mockTransactions()

    override fun mockNotifications(): ArrayList<Notification> = mMockHelper.mockNotifications()

    override fun mockCharities(): ArrayList<Charity> = mMockHelper.mockCharities()

    override fun mockProjects(): ArrayList<Project> = mMockHelper.mockProjects()

    override fun signup(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceHelper.signup(data)

    override fun sendOtp(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceHelper.sendOtp(data)

    override fun resolveCardNumber(bin: String): Observable<Response> = mPayStackServiceHelper.resolveCardNumber(bin)

    override fun validateOtp(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceHelper.validateOtp(data)

    override fun getUser(mobile: String, authorization: String): Observable<Response> = mRemoteServiceHelper.getUser(mobile, authorization)

    override fun getWallet(mobile: String, authorization: String): Observable<Response> = mRemoteServiceHelper.getWallet(mobile, authorization)

    override fun sendMoney(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.sendMoney(data, authorization, hash)

    override fun buyAirtime(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.buyAirtime(data, authorization, hash)

    override fun resolveBankAccount(accountNumber: String, bankCode: String): Observable<Response> = mPayStackServiceHelper.resolveBankAccount(accountNumber, bankCode)

    override fun requestPaymentLink(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.requestPaymentLink(data, authorization, hash)

    override fun fundWalletWithCard(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.fundWalletWithCard(data, authorization, hash)

    override fun signin(username: String, password: String, initializationVector: String, grantType: String): Observable<HashMap<String, Any>> = mRemoteServiceHelper.signin(username, password, initializationVector, grantType)

    override fun sendOtpForgotPassword(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.sendOtpForgotPassword(data, authorization, hash)

    override fun fundWalletWithBankAccount(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.fundWalletWithBankAccount(data, authorization, hash)

    override fun getUserTransactions(mobile: String, toDate: String, fromDate: String, authorization: String): Observable<Response> = mRemoteServiceHelper.getUserTransactions(mobile, toDate, fromDate, authorization)
}