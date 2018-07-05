package com.sterlingng.paylite.data.manager

import android.content.Context
import com.sterlingng.paylite.data.model.Category
import com.sterlingng.paylite.data.model.Charity
import com.sterlingng.paylite.data.model.Deal
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

    override fun mockCharities(): Observable<ArrayList<Charity>> {
        return mockHelper.mockCharities()
    }

    override fun mockCategories(): Observable<ArrayList<Category>> {
        return mockHelper.mockCategories()
    }

    override fun mockDeals(): Observable<ArrayList<Deal>> {
        return mockHelper.mockDeals()
    }
}